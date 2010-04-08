var PStrength=new Class(
{
Implements:[Options],
options: {
    verdicts:["Very weak","Weak","Medium","Strong","Strongest"],
    colors:["#f00","#c06","#f60","#3c0","#3d0"],
    scores:[10,15,30,40],
    common:["password","sex","god","123456","123","liverpool","letmein","qwerty","monkey"],
    minchar:8,
    where:'before',
    id:'required'
},

initialize: function(options) {
    this.setOptions(options);
    var o=this.options;
    var e=$(o.id);
    new Element('div',{'id':o.id+'_text'}).inject(e,o.where);
    new Element('div',{'id':o.id+'_bar',styles:{'border':'1px solid white','font-size':'1px;','height':'2px','width':'0px'}}).inject(e,o.where);
    e.addEvent('keyup',this.onkeyup.bind(this));    
},

onkeyup:function() {
    var o=this.options;
    var perc = this.checkPassword($(o.id).value);
    var bar = $(o.id + "_bar");
    var txt = $(o.id + "_text");
    var strColor;
    var strText;
    if (perc == -200) {
        strColor = "#f00";
        strText = "Unsafe password word!";
        bar.setStyles({width:"0%"});
    } else {
        if (perc < 0 && perc > -199) {
            strColor = "#ccc";
            strText = "Too short";
            bar.setStyles({width:"5%"});
        } else {
            if (perc <= o.scores[0]) {
                strColor = o.colors[0];
                strText = o.verdicts[0];
                bar.setStyles({width:"10%"});
            } else {
                if (perc > o.scores[0] && perc <= o.scores[1]) {
                    strColor = o.colors[1];
                    strText = o.verdicts[1];
                    bar.setStyles({width:"25%"});
                } else {
                    if (perc > o.scores[1] && perc <= o.scores[2]) {
                        strColor = o.colors[2];
                        strText = o.verdicts[2];
                        bar.setStyles({width:"50%"});
                    } else {
                        if (perc > o.scores[2] && perc <= o.scores[3]) {
                            strColor = o.colors[3];
                            strText = o.verdicts[3];
                            bar.setStyles({width:"75%"});
                        } else {
                            strColor = o.colors[4];
                            strText = o.verdicts[4];
                            bar.setStyles({width:"92%"});
                        }
                    }
                }
            }
        }
    }
    bar.setStyles({backgroundColor:strColor});
    txt.set('html','<span style="color:' + strColor + ';">' + strText + '</span>');
},

checkPassword: function(val) {
    var intScore = 0;
    // PASSWORD LENGTH
    if (val.length<5)                         // length 4 or less
    {
        intScore = (intScore + 3)
    }
    else if (val.length>4 && val.length<8) // length between 5 and 7
    {
        intScore = (intScore+6)
    }
    else if (val.length>7 && val.length<16)// length between 8 and 15
    {
        intScore = (intScore+12)
    }
    else if (val.length>15)                    // length 16 or more
    {
        intScore = (intScore+18)
    }
    // LETTERS (Not exactly implemented as dictacted above because of my limited understanding of Regex)
    if (val.match(/[a-z]/))                              // [verified] at least one lower case letter
    {
        intScore = (intScore+1)
    }
    if (val.match(/[A-Z]/))                              // [verified] at least one upper case letter
    {
        intScore = (intScore+5)
    }
    // NUMBERS
    if (val.match(/\d+/))                                 // [verified] at least one number
    {
        intScore = (intScore+5)
    }
    if (val.match(/(.*[0-9].*[0-9].*[0-9])/))             // [verified] at least three numbers
    {
        intScore = (intScore+5)
    }
    // SPECIAL CHAR
    if (val.match(/.[!,@,#,$,%,^,&,*,?,_,~]/))            // [verified] at least one special character
    {
        intScore = (intScore+5)
    }
    // [verified] at least two special characters
    if (val.match(/(.*[!,@,#,$,%,^,&,*,?,_,~].*[!,@,#,$,%,^,&,*,?,_,~])/))
    {
        intScore = (intScore+5)
    }
    // COMBOS
    if (val.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/))        // [verified] both upper and lower case
    {
        intScore = (intScore+2)
    }
    if (val.match(/([a-zA-Z])/) && val.match(/([0-9])/)) // [verified] both letters and numbers
    {
        intScore = (intScore+2)
    }
    // [verified] letters, numbers, and special characters
    if (val.match(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/))
    {
        intScore = (intScore+2)
    }
    return intScore;
}
    
});

window.nimble = window.nimble || {};
window.nimble.PStrength = function(e,options) {
    var o={'id':$(e).get('id')};
    if(options) o.extend(options);
    return new PStrength(o);
};