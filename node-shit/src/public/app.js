let doc ;
    doc =fetching()
    doc = {
        "result":[
            {
                "diff":"no",
                "paragraph_id":1,
                "author_id":1,
                "order":1,
                "changes":[],
                "date":"05.09.2019 17:03"
            },
        {
            "diff":"no",
            "paragraph_id":2,
            "author_id":1,
            "order":2,
            "changes":[],
            "date":"05.09.2019 17:03"
        },
        {
            "diff":"no",
            "paragraph_id":3,
            "author_id":1,
            "order":3,
            "changes":[],
            "date":"05.09.2019 17:03"},
        {
            "diff":"no",
            "paragraph_id":4,
            "author_id":1,
            "order":4,
            "changes":[],
            "date":"05.09.2019 17:03"},
        {
            "diff":"no",
            "paragraph_id":5,
            "author_id":1,
            "order":5,
            "changes":[],
            "date":"05.09.2019 17:03"
        },
        {
            "diff":"no",
            "paragraph_id":6,"author_id":1,
            "order":6,
            "changes":[],
            "date":"05.09.2019 17:03"},
        {
            "diff":"no","paragraph_id":7,
            "author_id":1,
            "order":7,
            "changes":[],
            "date":"05.09.2019 17:03"
        },
        {
            "diff":"no",
            "paragraph_id":8,
            "author_id":1,
            "order":8,
            "changes":[],
            "date":"05.09.2019 17:03"},
        {
            "diff":"no",
            "paragraph_id":9,
            "author_id":1,
            "order":9,
            "changes":[],
            "date":"05.09.2019 17:03"},
        {
            "diff":"removed",
            "paragraph_id":1,
            "author_id":0,
            "order":1,
            "changes":[],
            "date":"07.09.2019 11:06"
        }
        ,
        {
            "diff":"inserted",
            "paragraph_id":10,
            "author_id":2,
            "order":0,
            "changes":[],
            "date":"07.09.2019 11:04"
        },
        {
            "diff":"changed",
            "paragraph_id":2
            ,"author_id":2,"order":2,"changes":[{"type":"del","start":0,"end":1,"text":""},{"type":"ins","start":2,"end":2,"text":"Похож"},{"type":"del","start":7,"end":10,"text":""},{"type":"ins","start":11,"end":11,"text":"я"},{"type":"del","start":93,"end":94,"text":""},{"type":"ins","start":176,"end":176,"text":" "},{"type":"ins","start":220,"end":220,"text":"покрывают "},{"type":"del","start":237,"end":238,"text":""},{"type":"ins","start":239,"end":239,"text":"и"},{"type":"del","start":245,"end":255,"text":""}],"date":"07.09.2019 11:04"},{"diff":"changed","paragraph_id":3,"author_id":2,"order":3,"changes":[{"type":"del","start":70,"end":78,"text":""},{"type":"del","start":89,"end":90,"text":""},{"type":"ins","start":91,"end":91,"text":"ё"},{"type":"ins","start":201,"end":201,"text":"в хакатонах "},{"type":"ins","start":226,"end":226,"text":"и"},{"type":"del","start":227,"end":234,"text":""}],"date":"07.09.2019 11:04"},{"diff":"changed","paragraph_id":4,"author_id":3,"order":4,"changes":[{"type":"del","start":52,"end":54,"text":""},{"type":"ins","start":55,"end":55,"text":"08"},{"type":"del","start":57,"end":59,"text":""},{"type":"ins","start":60,"end":60,"text":"09"},{"type":"del","start":64,"end":66,"text":""},{"type":"ins","start":67,"end":67,"text":"19"}],"date":"07.09.2019 19:45"},{"diff":"changed","paragraph_id":7,"author_id":3,"order":7,"changes":[{"type":"del","start":91,"end":92,"text":""},{"type":"ins","start":93,"end":93,"text":"0"},{"type":"del","start":95,"end":97,"text":""},{"type":"ins","start":98,"end":98,"text":"09"},{"type":"del","start":102,"end":104,"text":""},{"type":"ins","start":105,"end":105,"text":"19"}],"date":"07.09.2019 19:45"},{"diff":"changed","paragraph_id":4,"author_id":7,"order":4,"changes":[{"type":"del","start":47,"end":48,"text":""},{"type":"ins","start":49,"end":49,"text":"Б"},{"type":"del","start":133,"end":134,"text":""},{"type":"ins","start":135,"end":135,"text":"е"},{"type":"del","start":136,"end":137,"text":""},{"type":"ins","start":138,"end":138,"text":"публ"},{"type":"del","start":142,"end":144,"text":""},{"type":"del","start":145,"end":147,"text":""},{"type":"ins","start":148,"end":148,"text":"и"},{"type":"del","start":149,"end":152,"text":""},{"type":"ins","start":153,"end":153,"text":"Б"},{"type":"del","start":154,"end":155,"text":""},{"type":"ins","start":156,"end":156,"text":"л"},{"type":"del","start":157,"end":160,"text":""},{"type":"ins","start":161,"end":161,"text":"русь"}],"date":"08.08.2019 09:21"},{"diff":"changed","paragraph_id":7,"author_id":7,"order":7,"changes":[{"type":"del","start":86,"end":87,"text":""},{"type":"ins","start":88,"end":88,"text":"Б"},{"type":"del","start":172,"end":173,"text":""},{"type":"ins","start":174,"end":174,"text":"е"},{"type":"del","start":175,"end":176,"text":""},{"type":"ins","start":177,"end":177,"text":"публ"},{"type":"del","start":181,"end":183,"text":""},{"type":"del","start":184,"end":186,"text":""},{"type":"ins","start":187,"end":187,"text":"и"},{"type":"del","start":188,"end":191,"text":""},{"type":"ins","start":192,"end":192,"text":"Б"},{"type":"del","start":193,"end":194,"text":""},{"type":"ins","start":195,"end":195,"text":"л"},{"type":"del","start":196,"end":199,"text":""},{"type":"ins","start":200,"end":200,"text":"русь"}],"date":"08.08.2019 09:21"}]}

console.log(doc)

async function fetching() {
    await fetch("http://172.20.10.4:8080/doc?id=11",{credentials: "include"})
        .then(response=>{
            return response.json()
        })
        .then(function (defs) {
            console.log(defs);
            return defs;
        })
        .catch(
            // Отправить на сервер для метрики
        );
}
