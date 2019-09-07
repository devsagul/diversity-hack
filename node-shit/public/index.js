const elem=(id)=> {
    return document.getElementById(id)
}
const elems=(className)=>{
    return document.getElementsByClassName(className)
}
const text= "Lorem ipsum dolor sit amet, consectetur adipisicing " +
    "elit. Autem quia, corrupti accusamus odio, deserunt " +
    "dicta natus necessitatibus dolores " +
    "eligendi quis tempore? Tempora, " +
    "exercitationem consectetur tenetur, incidunt " +
    "reiciendis architecto beatae nesciunt sed quibusdam labore sequi amet ullam necessitatibus dolores tempore quas, dolor eius veniam nemo quasi quo voluptate. Fugit consequatur minima, repudiandae. Dignissimos cum omnis ratione debitis, voluptate cumque nesciunt impedit facere culpa labore alias nulla quod, assumenda asperiores. Rem quisquam consequuntur maiores aliquam illo rerum nihil pariatur delectus perspiciatis obcaecati, debitis commodi eaque in tenetur, velit explicabo non autem labore molestias? Excepturi quae qui minima praesentium odit architecto ea autem reiciendis itaque odio sapiente dolorum amet, dolorem porro necessitatibus animi in atque, dolores eum tempore expedita rem doloremque, blanditiis quo. Tempora impedit, nisi excepturi! Rerum repudiandae, totam. Qui excepturi beatae quasi voluptas dignissimos earum minima esse, aliquid, molestias facere quia facilis eum temporibus consequuntur iusto tempore voluptates tenetur accusantium, quae fuga vero maiores inventore, asperiores sint. Praesentium officiis nihil illum culpa delectus? Autem architecto numquam vero nostrum, explicabo quia similique. Cumque doloremque quae praesentium deserunt dicta odio, debitis est iusto aut officia perferendis molestias consequuntur itaque atque a recusandae soluta, similique, modi amet quo. Omnis, error. A inventore soluta voluptatibus! Eius, veniam distinctio ut! Ducimus nisi ea quam recusandae, quidem commodi, est exercitationem illum esse saepe numquam amet culpa nobis adipisci ad quo nemo fuga, asperiores assumenda, laboriosam incidunt obcaecati eligendi facilis neque magnam? Porro modi, minima recusandae assumenda ratione nulla sed provident repellendus consequuntur possimus cupiditate nihil perferendis ut?";
elem("text").innerText =text;
elem("text").addEventListener("input", function(event) {
 //console.log(elem("text").innerHTML.charAt(getCaretPosition()));
 //console.log(newContent)
}, false);
let changes = () =>{
    const caretPos = getCaretPosition();
    const originContent= getContext(caretPos,text);
    const newContent= getContext(caretPos,elem("text").innerHTML);
    if (newContent !== originContent) {
        elem("text").innerHTML = elem("text").innerHTML.replace(newContent, "<div class='admin' style='background: aqua'>" + originContent + "</div>" + "<div class='user' style='background: yellow'>" + newContent + "</div>")
        const adminPosts =elems("admin")
        adminPosts[adminPosts.length-1].innerHTML +=`
        <div style="
        padding-top: -40px!important;
        background: aqua;
        display: inline-table;
        position: absolute;
        right: 40px;
        "> A </div>
        `;
        const userPosts =elems("user")
        userPosts[userPosts.length-1].innerHTML +=`
        <div style="
        display: inline-table;
        padding-top: -20px; !important;
        position: absolute;
        right: 40px;
         background: yellow;
        "> U </div>
        `
    }

};
const getContext =(index,text)=>{
    let leftBorder=null;
    let rightBorder=null;
    let notOver = true;
    for (let x=index;x>0 && notOver;x--){
            if (text[x]==='.'||text[x]==='?'||text[x]==='!'){
                console.log(text[x]);
                leftBorder = x+1;
                notOver= false;
            }
    }
    notOver =true;
    for (let x=index;x<text.length && notOver;x++){
        if (text[x]==='.'||text[x]==='?'||text[x]==='!'){
            console.log(text[x]);
            rightBorder = x;
            notOver= false;
        }
    }
    if (leftBorder ===null){
        leftBorder=0
    }
    if (rightBorder===null){
        rightBorder = text.length;
    }
    return text.substr(leftBorder,rightBorder-leftBorder)
};


function getCaretCharacterOffsetWithin(element) {
    var caretOffset = 0;
    var doc = element.ownerDocument || element.document;
    var win = doc.defaultView || doc.parentWindow;
    var sel;
    if (typeof win.getSelection != "undefined") {
        sel = win.getSelection();
        if (sel.rangeCount > 0) {
            var range = win.getSelection().getRangeAt(0);
            var preCaretRange = range.cloneRange();
            preCaretRange.selectNodeContents(element);
            preCaretRange.setEnd(range.endContainer, range.endOffset);
            caretOffset = preCaretRange.toString().length;
        }
    } else if ((sel = doc.selection) && sel.type != "Control") {
        var textRange = sel.createRange();
        var preCaretTextRange = doc.body.createTextRange();
        preCaretTextRange.moveToElementText(element);
        preCaretTextRange.setEndPoint("EndToEnd", textRange);
        caretOffset = preCaretTextRange.text.length;
    }
    return caretOffset;
}

function getCaretPosition() {
    if (window.getSelection && window.getSelection().getRangeAt) {
        var range = window.getSelection().getRangeAt(0);
        var selectedObj = window.getSelection();
        var rangeCount = 0;
        var childNodes = selectedObj.anchorNode.parentNode.childNodes;
        for (var i = 0; i < childNodes.length; i++) {
            if (childNodes[i] == selectedObj.anchorNode) {
                break;
            }
            if (childNodes[i].outerHTML)
                rangeCount += childNodes[i].outerHTML.length;
            else if (childNodes[i].nodeType == 3) {
                rangeCount += childNodes[i].textContent.length;
            }
        }
        return range.startOffset + rangeCount;
    }
    return -1;
}

function showCaretPos() {
    var el = elem("text");
    var caretPosEl = elem("caretPos");
    caretPosEl.innerHTML = "Caret position: " + getCaretPosition(); //getCaretCharacterOffsetWithin(el);
}

document.body.onkeyup = showCaretPos;
document.body.onmouseup = showCaretPos;