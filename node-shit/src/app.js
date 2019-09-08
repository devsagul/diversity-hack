const express = require('express');
const bodyParser = require('body-parser');
const path = require("path");
const app = express();
const cors = require('cors');
app.use(cors());
app.set("public", path.join(__dirname, "./public"));
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static('./public'));

app.get('/', (req, res) => {
    res.sendFile(__dirname + '/public/index.html');
});
let port_number =process.env.PORT || 3000;
app.listen(port_number,'0.0.0.0', () => console.log('Example app listening on port 3000!'));
