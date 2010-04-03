/*
 * ProtoTabs 0.1
 *
 * Copyright (c) 2010 licensors/jquery/AUTHORS.txt (http://jqueryui.com/about)
 * MIT (licensors/jquery/MIT-LICENSE.txt)
 *
 * derived/converted from jQuery UI Tabs 1.8
 * Copyright (c) 2010 licensors/jquery/AUTHORS.txt (http://jqueryui.com/about)
 * Dual licensed under the MIT (licensors/jquery/MIT-LICENSE.txt)
 *
 * http://docs.jquery.com/UI/Tabs
 *
 * Depends:
 *	prototype.js
 */
var ProtoTabs=Class.create();

ProtoTabs.prototype =
{
    version: '0.1',
    tabId: 0,
	listId: 0,
	options: {
		add: null,
		ajaxOptions: null,
		cache: false,
		cookie: null, // e.g. { expires: 7, path: '/', domain: 'jquery.com', secure: true }
		collapsible: false,
		disable: null,
		disabled: $A([]),
		enable: null,
		event: 'click',
		idPrefix: 'ui-tabs-',
		load: null,
		panelTemplate: '<div></div>',
		remove: null,
		select: null,
		show: null,
		spinner: '<em>Loading&#8230;</em>',
		tabTemplate: '<li><a href="#{href}"><span>#{label}</span></a></li>'
	},
    
    initialize: function(options) {
        this.setOptions(options);
        
        var o=this.options;
        this.element=$(o.id);

        this.tabify(true);
    },

	setOptions: function(options) {
		Object.extend(this.options, options || {});
	},
    
	setOption: function(key, value) {
		if (key == 'selected') {
			if (this.options.collapsible && value == this.options.selected) {
				return;
			}
			this.select(value);
		}
		else {
			this.options[key] = value;
			this.tabify();
		}
	},

	getTabId: function(a) {
		return a.title && a.title.replace(/\s/g, '_').replace(/[^A-Za-z0-9\-_:\.]/g, '') ||
			this.options.idPrefix + (++tabId);
	},

	sanitizeSelector: function(hash) {
		return hash.replace(/\#/g, '').replace(/:/g, '\\:'); // we need this because an id may contain a ":"
	},

	cookie: function() {
		var cookie = this.cookie || (this.cookie = this.options.cookie.name || 'ui-tabs-' + (++listId));
		return $.cookie.apply(null, [cookie].concat($.makeArray(arguments)));
	},

	ui: function(tab, panel) {
		return {
			tab: tab,
			panel: panel,
			index: this.anchors.indexOf(tab)
		};
	},

	cleanup: function() {
		// restore all former loading tabs labels
		this.lis.filter(this.isProcessing).removeClassName('ui-state-processing')
                .filter(function(itm) {var e=itm.getElement('span'); return (e&&e.retrieve('label.tabs')); } )
				.each(function() {
					var el = $(this);
					el.html(el.retrieve('label.tabs')).store('label.tabs',null);
				});
	},

	tabify: function(init) {

		this.list = this.element.getElementsBySelector('ol,ul')[0]; //.eq(0);
		this.lis = $A(this.list.getElementsBySelector('li').filter(function(li) { return li.getElementsBySelector('a').length>0;}));
		this.anchors =  $A(this.lis.map(function(itm) { return itm.getElementsBySelector('a')[0]; }));
		this.panels = $A();

		var self = this, o = this.options;

		var fragmentId = /^#.+/; // Safari 2 reports '#' for an empty hash
		this.anchors.each(function(a,i) {
            a=$(a);
			var href = a.readAttribute('href');

			// For dynamically created HTML that contains a hash as href IE < 8 expands
			// such href to the full page url with hash and then misinterprets tab as ajax.
			// Same consideration applies for an added tab with a fragment identifier
			// since a[href=#fragment-identifier] does unexpectedly not match.
			// Thus normalize href attribute...
			var hrefBase = href.split('#')[0], baseEl;
			if (hrefBase && (hrefBase === location.toString().split('#')[0] ||
					(baseEl = $('base')[0]) && hrefBase === baseEl.href)) {
				href = a.hash;
				a.href = href;
			}

			// inline tab
			if (fragmentId.test(href)) {
				self.panels.push(self.sanitizeSelector(href));
                self.panels = self.panels.uniq();
			}

			// remote tab
			else if (href != '#') { // prevent loading the page itself if href is just "#"
                /// TODO: prototype remote load not finished
                a.store('href.tabs',href); // required for restore on destroy
                a.store('load.tabs',href.replace(/#.*$/, ''));

				var id = self.getTabId(a);
				a.href = '#' + id;
				var $panel = $('#' + id);
				if (!$panel.length) {
					$panel = $(o.panelTemplate).attr('id', id).addClassName('ui-tabs-panel ui-widget-content ui-corner-bottom')
						.insert(self.panels[i - 1] || self.list,'after');
					$panel.store('destroy.tabs', true);
				}
				self.panels.push($panel);
                self.panels = self.panels.uniq();
			}

			// invalid tab href
			else {
				o.disabled.push(i);
			}
		});

		// initialization from scratch
		if (init) {
			// attach necessary classes for styling
			this.element.addClassName('ui-tabs ui-widget ui-widget-content ui-corner-all');
			this.list.addClassName('ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all');
			this.lis.addClassName('ui-state-default ui-corner-top');
			this.panels.addClassName('ui-tabs-panel ui-widget-content ui-corner-bottom');

			// Selected tab
			// use "selected" option or try to retrieve:
			// 1. from fragment identifier in url
			// 2. from cookie
			// 3. from selected class attribute on <li>
			if (o.selected === undefined) {
				if (location.hash) {
					this.anchors.each(function(a, i) {
						if (a.hash == location.hash) {
							o.selected = i;
							return false; // break
						}
					});
				}
				if (typeof o.selected != 'number' && o.cookie) {
					o.selected = parseInt(self.cookie(), 10);
				}
				if (typeof o.selected != 'number' && this.lis.filter(this.isSelected).length) {
					o.selected = this.lis.indexOf(this.lis.filter(this.isSelected)[0]);
				}
				o.selected = o.selected || (this.lis.length ? 0 : -1);
			}
			else if (o.selected === null) { // usage of null is deprecated, TODO remove in next release
				o.selected = -1;
			}

			// sanity check - default to first tab...
			o.selected = ((o.selected >= 0 && this.anchors[o.selected]) || o.selected < 0) ? o.selected : 0;

			// Take disabling tabs via class attribute from HTML
			// into account and update option properly.
			// A selected tab cannot become disabled.
            this.lis.filter(this.isDisabled).each(function(li,i) { o.disabled.include(i); });
			o.disabled = o.disabled.sort();
            o.disabled=o.disabled.without(o.selected);

			// highlight selected tab
			this.panels.addClassName('ui-tabs-hide');
			this.lis.removeClassName('ui-tabs-selected ui-state-active');
			if (o.selected >= 0 && this.anchors.length) { // check for length avoids error when initializing empty list
                this.panels.each(function(itm,i) { if(i == o.selected) $(itm).removeClassName('ui-tabs-hide'); });
                this.lis.each(function(itm,i) { if(i == o.selected) $(itm).addClassName('ui-tabs-selected ui-state-active'); });

				// seems to be expected behavior that the show callback is fired
				self.element.fire('show');
				this.load(o.selected);
			}
		}
		// update selected after add/remove
		else {
			o.selected = this.lis.indexOf(this.lis.filter(this.isSelected)[0]);
		}

		// update collapsible
		this.element[o.collapsible ? 'addClassName' : 'removeClassName']('ui-tabs-collapsible');

		// set or update cookie after init and add/remove respectively
		if (o.cookie) { this.cookie(o.selected, o.cookie); }

		// disable tabs
		for (var i = 0, li; (li = this.lis[i]); i++) {
			if($(li).hasClassName('ui-tabs-selected') || o.disabled.indexOf(i)<0) $(li).removeClassName('ui-state-disabled');
            else $(li).addClassName('ui-state-disabled');
		}

		// remove all handlers before, tabify may run on existing tabs after add or option change
		this.lis.concat(this.anchors).stopObserving();

		if (o.event != 'mouseover') {
			var addState = function(state, el) {
				if (!el.hasClassName('ui-state-disabled')) {
					el.addClassName('ui-state-' + state);
				}
			};
			var removeState = function(state, el) {
				el.removeClassName('ui-state-' + state);
			};
			this.lis.observe('mouseover', function() {
				addState('hover', $(this));
			});
			this.lis.observe('mouseout', function() {
				removeState('hover', $(this));
			});
			this.anchors.observe('focus', function() {
				addState('focus', $(this).up('li'));
			});
			this.anchors.observe('blur', function() {
				removeState('focus', $(this).up('li'));
			});
		}

		var showTab = function(clicked, $show) {
				var p=$(clicked).up('li');
                p.addClassName('ui-tabs-selected');
                p.addClassName('ui-state-active');
				$show.removeClassName('ui-tabs-hide');
                self.element.dequeue("tabs");
			};

		var hideTab =function(clicked, $hide, $show) {
                self.lis.removeClassName('ui-tabs-selected');
                self.lis.removeClassName('ui-state-active');
                showTab(clicked, $show);
                if(typeof $hide=='array') $hide.each(function(itm) { $(itm).addClassName('ui-tabs-hide'); } );
				else $($hide).addClassName('ui-tabs-hide');
                self.fire('hide');
			};

		// attach tab event handler, stopObeserving to avoid duplicates from former tabifying...
		this.anchors.observe(o.event, function() {
			var el = this;
            var $li = $(this).up('li');
            var $hide = self.panels.filter(self.isNotHidden);
			var $show = $(self.sanitizeSelector(this.hash));

			// If tab is already selected and not collapsible or tab disabled or
			// or is already loading or click callback returns false stop here.
			// Check if click handler returns false last so that it is not executed
			// for a disabled or loading tab!
			if (($li.hasClassName('ui-tabs-selected') && !o.collapsible) ||
				$li.hasClassName('ui-state-disabled') ||
				$li.hasClassName('ui-state-processing')) {
				this.blur();
				return false;
			}

			o.selected = self.anchors.indexOf(this);

			self.abort();

			// if tab may be closed
			if (o.collapsible) {
				if ($li.hasClassName('ui-tabs-selected')) {
					o.selected = -1;

					if (o.cookie) {
						self.cookie(o.selected, o.cookie);
					}

					self.element.queue("tabs", function() {
						hideTab(el, $hide, $show);
					}.bindAsEventListener(this)).dequeue("tabs");

					this.blur();
					return false;
				}
				else if (!$hide.length) {
					if (o.cookie) {
						self.cookie(o.selected, o.cookie);
					}

					self.element.queue("tabs", function() {
						showTab(el, $show);
					}.bindAsEventListener(this));

					self.load(self.anchors.indexOf(this)); // TODO make passing in node possible, see also http://dev.jqueryui.com/ticket/3171

					this.blur();
					return false;
				}
			}

			if (o.cookie) {
				self.cookie(o.selected, o.cookie);
			}

			// show new tab
			if ($show) {
				if ($hide) {
					self.element.queue("tabs", function() {
						hideTab(el, $hide, $show);
					});
				}
				self.element.queue("tabs", function() {
					showTab(el, $show);
				});

				self.load(self.anchors.indexOf(this));
			}
			else {
				throw 'jQuery UI Tabs: Mismatching fragment identifier.';
			}

			// Prevent IE from keeping other link focussed when using the back button
			// and remove dotted border from clicked link. This is controlled via CSS
			// in modern browsers; blur() removes focus from address bar in Firefox
			// which can become a usability and annoying problem with tabs('rotate').
			if (Browser.Engine.trident) {
				this.blur();
			}

		});

		// disable click in any case
		this.anchors.observe('click', function(){return false;});
	},

	destroy: function() {
		var o = this.options;

		this.abort();

		this.element.stopObserving()
			.removeClassName('ui-tabs ui-widget ui-widget-content ui-corner-all ui-tabs-collapsible')
			.store('tabs',null);

		this.list.removeClassName('ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all');

		this.anchors.each(function() {
			var href = this.retrieve('href.tabs');
			if (href) {
				this.href = href;
			}
			var $this = $(this).stopObserving();
			$.each(['href', 'load', 'cache'], function(i, prefix) {
				$this.store(prefix + '.tabs',null);
			});
		});

        this.lis.stopObserving().combine(this.panels).each(function(e) {
			if (this.retrieve('destroy.tabs',false)) {
				$(this).remove();
			}
			else {
                $A([
					'ui-state-default',
					'ui-corner-top',
					'ui-tabs-selected',
					'ui-state-active',
					'ui-state-hover',
					'ui-state-focus',
					'ui-state-disabled',
					'ui-tabs-panel',
					'ui-widget-content',
					'ui-corner-bottom',
					'ui-tabs-hide'
				]).each(function(cls) { e.removeClassName(cls); });
			}
		});

		if (o.cookie) {
			this.cookie(null, o.cookie);
		}

		return this;
	},

	add: function(url, label, index) {
		if (index === undefined) {
			index = this.anchors.length; // append by default
		}

		var self = this, o = this.options,
			$li = $(o.tabTemplate.replace(/#\{href\}/g, url).replace(/#\{label\}/g, label)),
			id = !url.indexOf('#') ? url.replace('#', '') : this.getTabId($('a', $li)[0]);

		$li.addClassName('ui-state-default ui-corner-top').store('destroy.tabs', true);

		// try to find an existing element before creating a new one
		var $panel = $('#' + id);
		if (!$panel.length) {
			$panel = $(o.panelTemplate).attr('id', id).store('destroy.tabs', true);
		}
		$panel.addClassName('ui-tabs-panel ui-widget-content ui-corner-bottom ui-tabs-hide');

		if (index >= this.lis.length) {
			$li.appendTo(this.list);
			$panel.appendTo(this.list[0].parentNode);
		}
		else {
			$li.inject(this.lis[index],'before');
			$panel.inject(this.panels[index],'before');
		}

		o.disabled = $.map(o.disabled,
			function(n, i) { return n >= index ? ++n : n; });

		this.tabify();

		if (this.anchors.length == 1) { // after tabify
			o.selected = 0;
			$li.addClassName('ui-tabs-selected ui-state-active');
			$panel.removeClassName('ui-tabs-hide');
			this.element.queue("tabs", function() {
				self.fire('show');//, null, self.ui(self.anchors[0], self.panels[0]));
			});

			this.load(0);
		}

		// callback
		this.fire('add');
		return this;
	},

	remove: function(index) {
		var o = this.options, $li = this.lis.eq(index).remove(),
			$panel = this.panels.eq(index).remove();

		// If selected tab was removed focus tab to the right or
		// in case the last tab was removed the tab to the left.
		if ($li.hasClassName('ui-tabs-selected') && this.anchors.length > 1) {
			this.select(index + (index + 1 < this.anchors.length ? 1 : -1));
		}

		o.disabled = $.map($.grep(o.disabled, function(n, i) { return n != index; }),
			function(n, i) { return n >= index ? --n : n; });

		this.tabify();

		// callback
		this.fire('remove');
		return this;
	},

	enable: function(index) {
		var o = this.options;
		if ($.inArray(index, o.disabled) == -1) {
			return;
		}

		this.lis.eq(index).removeClassName('ui-state-disabled');
		o.disabled = $.grep(o.disabled, function(n, i) { return n != index; });

		// callback
		this.fire('enable');
		return this;
	},

	disable: function(index) {
		var o = this.options;
		if (index != o.selected) { // cannot disable already selected tab
			this.lis.eq(index).addClassName('ui-state-disabled');

			o.disabled.push(index);
			o.disabled.sort();

			// callback
			this.fire('disable');
		}

		return this;
	},

	select: function(index) {
		if (typeof index == 'string') {
			index = this.anchors.indexOf(this.anchors.filter(function(a) { return a.href==index; }));
		}
		else if (index === null) { // usage of null is deprecated, TODO remove in next release
			index = -1;
		}
		if (index == -1 && this.options.collapsible) {
			index = this.options.selected;
		}

		this.anchors.eq(index).fire(this.options.event);
		return this;
	},

	load: function(index) {
		var self = this, o = this.options, a = this.anchors[index], url = a.retrieve('load.tabs');

		//this.abort();

		// not remote or from cache
		if (!url || this.element.queue("tabs").length !== 0 && a.retrieve('cache.tabs')) {
			this.element.dequeue("tabs");
			return;
		}

		// load remote from here on
		this.lis.eq(index).addClassName('ui-state-processing');

		if (o.spinner) {
			var span = a.getElement('span');
			span.store('label.tabs', span.html()).set('html',o.spinner);
		}

		this.xhr = $.ajax($.extend({}, o.ajaxOptions, {
			url: url,
			success: function(r, s) {
				$(self.sanitizeSelector(a.hash)).html(r);

				// take care of tab labels
				self.cleanup();

				if (o.cache) {
					a.store('cache.tabs', true); // if loaded once do not load them again
				}

				// callbacks
				self.fire('load');
				try {
					o.ajaxOptions.success(r, s);
				}
				catch (e) {}
			},
			error: function(xhr, s, e) {
				// take care of tab labels
				self.cleanup();

				// callbacks
				self.fire('load');
				try {
					// Passing index avoid a race condition when this method is
					// called after the user has selected another tab.
					// Pass the anchor that initiated this request allows
					// loadError to manipulate the tab content panel via $(a.hash)
					o.ajaxOptions.error(xhr, s, index, a);
				}
				catch (e) {}
			}
		}));

		// last, so that load event is fired before show...
		self.element.dequeue("tabs");

		return this;
	},

	abort: function() {
		// stop possibly running animations
		this.element.queue([]);
		//this.panels.stop(false, true);

		// "tabs" queue must not contain more than two elements,
		// which are the callbacks for the latest clicked tab...
		this.element.queue("tabs", this.element.queue("tabs").splice(-2, 2));

		// terminate pending requests from other tabs
		if (this.xhr) {
			this.xhr.abort();
			delete this.xhr;
		}

		// take care of tab labels
		this.cleanup();
		return this;
	},

	url: function(index, url) {
		this.anchors.eq(index).store('cache.tabs',null).store('load.tabs', url);
		return this;
	},

	length: function() {
		return this.anchors.length;
	},

    isDisabled: function(itm) { return $(itm).hasClassName('ui-state-disabled'); },
    isSelected: function(itm) { return $(itm).hasClassName('ui-state-selected'); },
    isProcessing: function(itm) { return $(itm).hasClassName('ui-state-processing'); },
    isNotHidden: function(itm) { return !$(itm).hasClassName('ui-tabs-hide'); }
};

window.nimble = window.nimble || {};
window.nimble.Tabs = function(e,options) {
    var o={'id':$(e).identify()};
    if(options) o.extend(options);
    return new ProtoTabs(o);
};

document.observe("dom:loaded", function() {

Function.prototype.method = function (name, fn) {
    this.prototype[name] = fn;
    return this;
};

Element.method('clear', function() {
    this.innerHTML='';
    return this;
});

Array.method('stopObserving', function(cls,func) { this.each(function(e) { $(e).stopObserving(cls,func); }); return this; });
Array.method('observe', function(cls,func) { this.each(function(e) { $(e).observe(cls,func); }); return this; });
Array.method('addClassName', function(cls) { this.each(function(e) { $(e).addClassName(cls); }); return this; });
Array.method('removeClassName', function(cls) { this.each(function(e) { $(e).removeClassName(cls); }); return this; });

Element.method('queue',function(p1,p2) {
        var name="fx";
        if(typeof p1=='string') name=p1;
        if(typeof p1=='function' || typeof p1=='array') p2=p1;

        var q=this.retrieve("queue",$H({}));
        if(!q[name]) q[name]=$A([]);
        if(typeof p2=='array') {
            q[name]=q[name].concat(p2).uniq();
        }
        if(typeof p2=='function') q[name].push(p2);

        this.store("queue",q);
        return q[name];
    });

Element.method('dequeue', function(name) {
        var q=this.retrieve("queue",$H({}));
        if(!name) name="fx";
        if(!q[name]) return this;
        if(q[name].length==0) return this;
        var func=q[name].pop();
        this.store("queue",q);
        if(!func) return;
        if(q[name].length>0) func(q[name][0]);
        else func();
        return this;
    });

});
