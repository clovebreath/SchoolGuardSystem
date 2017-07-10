function $(dom) {
    return new test(dom);
}
 
function test(dom) {
    var _this = this;
    var prefix = dom.charAt(0);
    switch (prefix) {
      case ".":
        dom = document.getElementsByClassName(dom.slice(1));
        break;
 
      case "#":
        dom = document.getElementById(dom.slice(1));
        break;
 
      default:
        dom = document.getElementsByTagName(dom);
        break;
    }
    _this.domArr = [];
    if (dom.constructor == NodeList) {
        for (var i = 0; i < dom.length; i++) {
            _this.domArr.push(dom.item(i));
        }
    } else if (dom.constructor == HTMLDivElement) {
        _this.domArr.push(dom);
    } else {
        return false;
    }
    _this.css = function(name, val) {
        var styleObj = {};
        if (typeof name == "object") {
            for (var i in name) {
                styleObj[i] = name[i];
            }
        } else {
            styleObj[name] = val;
        }
        for (var name in styleObj) {
            for (var i = 0; i < _this.domArr.length; i++) {
                _this.domArr[i].style[name] = styleObj[name];
            }
        }
        return _this;
    };
    return _this;
}

//$("#content").css("width", "1190px");
//
//$(".content").css("width", "1000px");
// 
//$("span").css({
//    width:"50px",
//    height:"100px",
//    display:"block",
//    "background-color":"#CCC",
//    "margin-top":"20px"
//});