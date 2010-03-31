/*
 * MooTools Tabs 0.1
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
 *	mootools - core
 */
var MooTabs=new Class(
{
    Implements:[Options,Events],    
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
		disabled: [],
		enable: null,
		event: 'click',
		fx: null, // e.g. { height: 'toggle', opacity: 'toggle', duration: 200 }
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
        /// TODO; finish translation
		//var cookie = this.cookie || (this.cookie = this.options.cookie.name || 'ui-tabs-' + (++listId));
		//return $.cookie.apply(null, [cookie].concat($.makeArray(arguments)));
        return '';
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
		this.lis.filter(this.isProcessing).removeClass('ui-state-processing')
                .filter(function(itm) {var e=itm.getElement('span'); return (e&&e.retrieve('label.tabs')); } )
				.each(function() {
					var el = $(this);
					el.html(el.retrieve('label.tabs')).removeData('label.tabs');
				});
	},

	tabify: function(init) {

		this.list = this.element.getElements('ol,ul')[0]; //.eq(0);
		this.lis = this.list.getElements('li').filter(function(li) { return li.getElement('a')!=null;});
		this.anchors = this.lis.map(function(itm) { return itm.getElement('a'); });
		this.panels = $A([]);

		var self = this, o = this.options;

		var fragmentId = /^#.+/; // Safari 2 reports '#' for an empty hash
		this.anchors.each(function(a,i) {
			var href = $(a).get('href');

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
				self.panels = self.panels.include(self.sanitizeSelector(href));
			}

			// remote tab
			else if (href != '#') { // prevent loading the page itself if href is just "#"
                /// TODO: mootools remote load not finished
                a.store('href.tabs',href); // required for restore on destroy
                a.store('load.tabs',href.replace(/#.*$/, ''));

				var id = self.getTabId(a);
				a.href = '#' + id;
				var $panel = $('#' + id);
				if (!$panel.length) {
					$panel = $(o.panelTemplate).attr('id', id).addClass('ui-tabs-panel ui-widget-content ui-corner-bottom')
						.insert(self.panels[i - 1] || self.list,'after');
					$panel.store('destroy.tabs', true);
				}
				self.panels = self.panels.include($panel);
			}

			// invalid tab href
			else {
				o.disabled.push(i);
			}
		});

		// initialization from scratch
		if (init) {
			// attach necessary classes for styling
			this.element.addClass('ui-tabs ui-widget ui-widget-content ui-corner-all');
			this.list.addClass('ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all');
			this.lis.addClass('ui-state-default ui-corner-top');
			this.panels.addClass('ui-tabs-panel ui-widget-content ui-corner-bottom');

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
				if ($type(o.selected) != 'number' && o.cookie) {
					o.selected = parseInt(self.cookie(), 10);
				}
				if ($type(o.selected) != 'number' && this.lis.filter(this.isSelected).length) {
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
            o.disabled.erase(o.selected);

			// highlight selected tab
			this.panels.addClass('ui-tabs-hide');
			this.lis.removeClass('ui-tabs-selected ui-state-active');
			if (o.selected >= 0 && this.anchors.length) { // check for length avoids error when initializing empty list
                this.panels.each(function(itm,i) { if(i == o.selected) $(itm).removeClass('ui-tabs-hide'); });
                this.lis.each(function(itm,i) { if(i == o.selected) $(itm).addClass('ui-tabs-selected ui-state-active'); });

				// seems to be expected behavior that the show callback is fired
				self.element.fireEvent('show');
				this.load(o.selected);
			}

			// clean up to avoid memory leaks in certain versions of IE 6
			$(window).addEvent('unload', function() {
				$A([]).combine(self.lis).combine(self.anchors).removeEvents();
				self.lis = self.anchors = self.panels = null;
			});

		}
		// update selected after add/remove
		else {
			o.selected = this.lis.indexOf(this.lis.filter(this.isSelected)[0]);
		}

		// update collapsible
		this.element[o.collapsible ? 'addClass' : 'removeClass']('ui-tabs-collapsible');

		// set or update cookie after init and add/remove respectively
		if (o.cookie) { this.cookie(o.selected, o.cookie); }

		// disable tabs
		for (var i = 0, li; (li = this.lis[i]); i++) {
			if($(li).hasClass('ui-tabs-selected') || o.disabled.indexOf(i)<0) $(li).removeClass('ui-state-disabled');
            else $(li).addClass('ui-state-disabled');
		}

		// reset cache if switching from cached to not cached
        /// TODO: figure out how the are using cache
//		if (o.cache === false) {
//			this.anchors.removeData('cache.tabs');
//		}

		// remove all handlers before, tabify may run on existing tabs after add or option change
		$A([]).combine(this.lis).combine(this.anchors).removeEvents();

		if (o.event != 'mouseover') {
			var addState = function(state, el) {
				if (!el.hasClass('ui-state-disabled')) {
					el.addClass('ui-state-' + state);
				}
			};
			var removeState = function(state, el) {
				el.removeClass('ui-state-' + state);
			};
			this.lis.addEvent('mouseover', function() {
				addState('hover', $(this));
			});
			this.lis.addEvent('mouseout', function() {
				removeState('hover', $(this));
			});
			this.anchors.addEvent('focus', function() {
				addState('focus', $(this).getParent('li'));
			});
			this.anchors.addEvent('blur', function() {
				removeState('focus', $(this).getParent('li'));
			});
		}

		// set up animations
		var hideFx, showFx;
		if (o.fx) {
			if ($.isArray(o.fx)) {
				hideFx = o.fx[0];
				showFx = o.fx[1];
			}
			else {
				hideFx = showFx = o.fx;
			}
		}

		// Reset certain styles left over from animation
		// and prevent IE's ClearType bug...
		function resetStyle($el, fx) {
			$el.css({ display: '' });
			if (!$.support.opacity && fx.opacity) {
				$el[0].style.removeAttribute('filter');
			}
		}


/*		// Show a tab...
		var showTab = showFx ?
			function(clicked, $show) {
				$(clicked).closest('li').addClass('ui-tabs-selected ui-state-active');
				$show.hide().removeClass('ui-tabs-hide') // avoid flicker that way
					.animate(showFx, showFx.duration || 'normal', function() {
						resetStyle($show, showFx);
						self.fireEvent('show');
					});
			} :*/
		var showTab = function(clicked, $show) {
				var p=$(clicked).getParent('li');
                p.addClass('ui-tabs-selected');
                p.addClass('ui-state-active');
				$show.removeClass('ui-tabs-hide');
				self.fireEvent('show');
                self.element.dequeue("tabs");
			};

 /*		// Hide a tab, $show is optional...
		var hideTab = hideFx ?
			function(clicked, $hide) {
				$hide.animate(hideFx, hideFx.duration || 'normal', function() {
					self.lis.removeClass('ui-tabs-selected ui-state-active');
					$hide.addClass('ui-tabs-hide');
					resetStyle($hide, hideFx);
					self.element.dequeue("tabs");
				});
			} :*/

		var hideTab =function(clicked, $hide, $show) {
                self.lis.removeClass('ui-tabs-selected');
                self.lis.removeClass('ui-state-active');
                showTab(clicked, $show);
                if($type($hide)=='array') $hide.each(function(itm) { $(itm).addClass('ui-tabs-hide'); } );
				else $($hide).addClass('ui-tabs-hide');
                self.fireEvent('hide');
			};

		// attach tab event handler, removeEvent to avoid duplicates from former tabifying...
		this.anchors.addEvent(o.event, function() {
			var el = this;
            var $li = $(this).getParent('li');
            var $hide = self.panels.filter(self.isNotHidden);
			var $show = $(self.sanitizeSelector(this.hash));

			// If tab is already selected and not collapsible or tab disabled or
			// or is already loading or click callback returns false stop here.
			// Check if click handler returns false last so that it is not executed
			// for a disabled or loading tab!
			if (($li.hasClass('ui-tabs-selected') && !o.collapsible) ||
				$li.hasClass('ui-state-disabled') ||
				$li.hasClass('ui-state-processing')) {
				//self.fireEvent('select')) { //, null, self.ui(this, $show[0])) === false) {
				this.blur();
				return false;
			}

			o.selected = self.anchors.indexOf(this);

			self.abort();

			// if tab may be closed
			if (o.collapsible) {
				if ($li.hasClass('ui-tabs-selected')) {
					o.selected = -1;

					if (o.cookie) {
						self.cookie(o.selected, o.cookie);
					}

					self.element.queue("tabs", function() {
						hideTab(el, $hide, $show);
					}).dequeue("tabs");

					this.blur();
					return false;
				}
				else if (!$hide.length) {
					if (o.cookie) {
						self.cookie(o.selected, o.cookie);
					}

					self.element.queue("tabs", function() {
						showTab(el, $show);
					});

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
		this.anchors.addEvent('click', function(){return false;});
	},

	destroy: function() {
		var o = this.options;

		this.abort();

		this.element.removeEvents()
			.removeClass('ui-tabs ui-widget ui-widget-content ui-corner-all ui-tabs-collapsible')
			.removeData('tabs');

		this.list.removeClass('ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all');

		this.anchors.each(function() {
			var href = this.retrieve('href.tabs');
			if (href) {
				this.href = href;
			}
			var $this = $(this).removeEvents();
			$.each(['href', 'load', 'cache'], function(i, prefix) {
				$this.removeData(prefix + '.tabs');
			});
		});

        $A([]).combine(this.lis).removeEvents().combine(this.panels).each(function(e) {
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
				]).each(function(cls) { e.removeClass(cls); });
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

		$li.addClass('ui-state-default ui-corner-top').store('destroy.tabs', true);

		// try to find an existing element before creating a new one
		var $panel = $('#' + id);
		if (!$panel.length) {
			$panel = $(o.panelTemplate).attr('id', id).store('destroy.tabs', true);
		}
		$panel.addClass('ui-tabs-panel ui-widget-content ui-corner-bottom ui-tabs-hide');

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
			$li.addClass('ui-tabs-selected ui-state-active');
			$panel.removeClass('ui-tabs-hide');
			this.element.queue("tabs", function() {
				self.fireEvent('show');//, null, self.ui(self.anchors[0], self.panels[0]));
			});

			this.load(0);
		}

		// callback
		this.fireEvent('add');//, null, this.ui(this.anchors[index], this.panels[index]));
		return this;
	},

	remove: function(index) {
		var o = this.options, $li = this.lis.eq(index).remove(),
			$panel = this.panels.eq(index).remove();

		// If selected tab was removed focus tab to the right or
		// in case the last tab was removed the tab to the left.
		if ($li.hasClass('ui-tabs-selected') && this.anchors.length > 1) {
			this.select(index + (index + 1 < this.anchors.length ? 1 : -1));
		}

		o.disabled = $.map($.grep(o.disabled, function(n, i) { return n != index; }),
			function(n, i) { return n >= index ? --n : n; });

		this.tabify();

		// callback
		this.fireEvent('remove');//, null, this.ui($li.find('a')[0], $panel[0]));
		return this;
	},

	enable: function(index) {
		var o = this.options;
		if ($.inArray(index, o.disabled) == -1) {
			return;
		}

		this.lis.eq(index).removeClass('ui-state-disabled');
		o.disabled = $.grep(o.disabled, function(n, i) { return n != index; });

		// callback
		this.fireEvent('enable');//, null, this.ui(this.anchors[index], this.panels[index]));
		return this;
	},

	disable: function(index) {
		var o = this.options;
		if (index != o.selected) { // cannot disable already selected tab
			this.lis.eq(index).addClass('ui-state-disabled');

			o.disabled.push(index);
			o.disabled.sort();

			// callback
			this.fireEvent('disable');//, null, this.ui(this.anchors[index], this.panels[index]));
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

		this.anchors.eq(index).fireEvent(this.options.event);
		return this;
	},

	load: function(index) {
		var self = this, o = this.options, a = this.anchors[index], url = a.retrieve('load.tabs');

		this.abort();

		// not remote or from cache
		if (!url || this.element.queue("tabs").length !== 0 && a.retrieve('cache.tabs')) {
			this.element.dequeue("tabs");
			return;
		}

		// load remote from here on
		this.lis.eq(index).addClass('ui-state-processing');

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
				self.fireEvent('load');//, null, self.ui(self.anchors[index], self.panels[index]));
				try {
					o.ajaxOptions.success(r, s);
				}
				catch (e) {}
			},
			error: function(xhr, s, e) {
				// take care of tab labels
				self.cleanup();

				// callbacks
				self.fireEvent('load');//, null, self.ui(self.anchors[index], self.panels[index]));
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
		this.anchors.eq(index).removeData('cache.tabs').store('load.tabs', url);
		return this;
	},

	length: function() {
		return this.anchors.length;
	},

    isDisabled: function(itm) { return $(itm).hasClass('ui-state-disabled'); },
    isSelected: function(itm) { return $(itm).hasClass('ui-state-selected'); },
    isProcessing: function(itm) { return $(itm).hasClass('ui-state-processing'); },
    isNotHidden: function(itm) { return !$(itm).hasClass('ui-tabs-hide'); }
});
window.nimble = window.nimble || {};
window.nimble.Tabs = function(e,options) {
    var o={'id':$(e).get('id')};
    if(options) o.extend(options);
    return new MooTabs(o);
};

/*
 * Tabs Extensions
 */

/*
 * Rotate
 */
/*

$.extend($.ui.tabs.prototype, {
	rotation: null,
	rotate: function(ms, continuing) {

		var self = this, o = this.options;

		var rotate = self.rotate || (self._rotate = function(e) {
			clearTimeout(self.rotation);
			self.rotation = setTimeout(function() {
				var t = o.selected;
				self.select( ++t < self.anchors.length ? t : 0 );
			}, ms);

			if (e) {
				e.stopPropagation();
			}
		});

		var stop = self._unrotate || (self._unrotate = !continuing ?
			function(e) {
				if (e.clientX) { // in case of a true click
					self.rotate(null);
				}
			} :
			function(e) {
				t = o.selected;
				rotate();
			});

		// start rotation
		if (ms) {
			this.element.addEvent('tabsshow', rotate);
			this.anchors.addEvent(o.event + '.tabs', stop);
			rotate();
		}
		// stop rotation
		else {
			clearTimeout(self.rotation);
			this.element.removeEvent('tabsshow', rotate);
			this.anchors.removeEvent(o.event + '.tabs', stop);
			delete this._rotate;
			delete this._unrotate;
		}

		return this;
	}
});
*/

