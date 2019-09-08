fetching()
async function fetching() {
    await fetch("http://172.20.10.4:8080/doc?id=11",{credentials: "include"})
        .then(response=>{
            return response.json()
        })
        .then(function (defs) {
            console.log(defs)
            return defs;
        })
        .catch(
            // Отправить на сервер для метрики
        );
}
