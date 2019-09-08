const difs=[]
let id=0;
const elem=(id)=> {
    return document.getElementById(id)
};
const elems=(className)=>{
    return document.getElementsByClassName(className)
};
const text =  `Преподаватели вузовского курса информатики и программирования вынуждены часто в течение всего первого семестра вместо изучения положенных модулей, читают курс элементарной компьютерной алгебры. А курс программирования рассчитанный на студентов, не соотвествует текущему технологическому прорыву, как пример это препододования языка программирования C++ 2 версии, вместо С++ 17 версии.
Та же проблема наблюдается и у учащихся срдених образовательных учереждений. Школьникам, читают элементарную компьютерную безопасность или булеву алгебру. Для школьниковсуществуют технопарки, но они платные и не все компетенции покрывают.
Наш клуб Lambda МАИ помогает школьникам и студентам начальных курсов, тем что мы предподаем практическое использвание текущих технологий. Мы проводим лекции, семинары, приглашаем спикеров, участвуем и организуем хакатоны, занимаемся разработкой собственных проектов.
В соответствии ч3. Распоряжения Правительства РФ от 17.11.2008 № 1662-р «Концепции долгосрочного социально-экономического развития Российской Федерации на период до 2020 года» данный проект дает возможность развития человеческого потенциала включающего системные преобразования двух типов:
1. направленные на повышение конкурентоспособности кадрового потенциала, рабочей силы и социальных секторов экономики;
2. улучшающий качество социальной среды и условий жизни молодежи.
Деятельность нашего клуба полностью соответствует ч.3 п.4 Распоряжения Правительства РФ от 17.11.2008 № 1662-р «Концепции долгосрочного социально-экономического развития Российской Федерации на период до 2020 года» а именно решает следующие задачи, поставленные в концепции:
-обеспечение инновационного характера базового образования, в части обеспечения компетентностного подхода, взаимосвязи академических знаний и практических умений;
-модернизация институтов системы образования как инструментов социального развития, в части создания системы выявления и поддержки одаренных детей и талантливой молодежи, а также создания инфраструктуры социальной мобильности обучающихся`
elem("text").addEventListener("input", function(event) {
    //console.log(elem("text").innerHTML.charAt(getCaretPosition()));
 //console.log(newContent)
}, false);
elem("text").innerText=text;

let changes = () =>{
    const caretPos = getCaretPosition();
    const originContent= getContext(caretPos,text);
    const newContent= getContext(caretPos,elem("text").innerHTML);
    if (newContent !== originContent) {
        console.log(id)
        const dif =
            "<div id='"+id+"'><p style='height: 10px'/>"+
            "<div class='admin'><div contenteditable='false'><div id='target'>Target </div> <div id='time'>"+new Date().toTimeString()+" </div></div>" + originContent + "</div>" +
            "<button id='1from"+id+"' onclick='applyChanges(this, "+id+")'><img id='accept'></button><p style='height: 10px'/>"+
            "<p style='height: 10px'/>" +
            "<div class='user'><div contenteditable='false'><div id='target'>Target </div> <div id='time'>Time </div></div>" + newContent + "</div>"+
            "<button onclick='applyChanges(this,"+id+")' id='2from"+id+"'><img id='accept'/></button><p style='height: 10px'/>" +
            "</div>";
        elem("text").innerHTML = elem("text").innerHTML.replace(newContent,dif)
        difs.push({id:id,dif:dif});
        id++;
        const adminPosts =elems("admin")
        adminPosts[adminPosts.length-1].innerHTML +=`
        <img class="adminP P" src="https://cdn4.iconfinder.com/data/icons/people-avatar-1-1/128/29-512.png" alt="" height="20" width="20">`;
        const userPosts =elems("user")
        userPosts[userPosts.length-1].innerHTML +=`
        <img class="userP P" src="data:image/png;base64,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" alt="" width="20" height="20">`
        //elem(1).onclick = applyChanges;
        //elem(2).onclick = applyChanges;
    }

};
function applyChanges(event,idddd) {
    let acceptText
    console.log(elems("admin"));
    if (event.id[0]==1) {
        acceptText = elems("admin")[event.id[event.id.length - 1]];
    }
    else {
      acceptText = elems("user")[event.id[event.id.length - 1]];
    }
    let fullText=elem(idddd);
    acceptText.removeChild(acceptText.firstChild);
    console.log(acceptText)
    fullText.innerHTML = acceptText.innerText;
    id--;
}
const getContext =(index,text)=>{
    let leftBorder=null;
    let rightBorder=null;
    let notOver = true;
    for (let x=index;x>0 && notOver;x--){
            if (text[x]==='.'||text[x]==='?'||text[x]==='!'){
                leftBorder = x+1;
                notOver= false;
            }
    }
    notOver =true;
    for (let x=index;x<text.length && notOver;x++){
        if (text[x]==='.'||text[x]==='?'||text[x]==='!'){
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
for (let x=0;x<doc.result.length;x++){
    elem("text").innerHTML
}

function getCaretCharacterOffsetWithin(element) {
    var caretOffset = 0;
    var doc = element.ownerDocument || element.document;
    const win = doc.defaultView || doc.parentWindow;
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
    const el = elem("text");
    const caretPosEl = elem("caretPos");
    caretPosEl.innerHTML = "Caret position: " + getCaretPosition(); //getCaretCharacterOffsetWithin(el);
}

document.body.onkeyup = showCaretPos;
document.body.onmouseup = showCaretPos;

$(document).ready(function() {
    const handleDrag = function (e) {
        //kill any default behavior
        e.stopPropagation();
        e.preventDefault();
    };
    const handleDrop = function (e) {
        //kill any default behavior
        e.stopPropagation();
        e.preventDefault();
        //console.log(e);
        //get x and y coordinates of the dropped item
        x = e.clientX;
        y = e.clientY;
        //drops are treated as multiple files. Only dealing with single files right now, so assume its the first object you're interested in
        const file = e.dataTransfer.files[0];
        //don't try to mess with non-image files
        if (file.type.match('image.*')) {
            //then we have an image,

            //we have a file handle, need to read it with file reader!
            const reader = new FileReader();

            // Closure to capture the file information.
            reader.onload = (function (theFile) {
                //get the data uri
                const dataURI = theFile.target.result;
                //make a new image element with the dataURI as the source
                const img = document.createElement("img");
                img.src = dataURI;
                //Insert the image at the carat

                // Try the standards-based way first. This works in FF
                if (document.caretPositionFromPoint) {
                    const pos = document.caretPositionFromPoint(x, y);
                    range = document.createRange();
                    range.setStart(pos.offsetNode, pos.offset);
                    range.collapse();
                    range.insertNode(img);
                }
                // Next, the WebKit way. This works in Chrome.
                else if (document.caretRangeFromPoint) {
                    range = document.caretRangeFromPoint(x, y);
                    range.insertNode(img);
                } else {
                    //not supporting IE right now.
                    console.log('could not find carat');
                }
                    img.height =500
                    img.width =500
            });
            //this reads in the file, and the onload event triggers, which adds the image to the div at the carat
            reader.readAsDataURL(file);
        }
    };

    const dropZone = elem('text');
    dropZone.addEventListener('dragover', handleDrag, false);
    dropZone.addEventListener('drop', handleDrop, false);
});