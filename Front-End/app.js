const express = require('express');
const axios = require('axios');
const bodyParser = require('body-parser');

const app = express();
app.use(express.static(__dirname + '/public'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.get('/', (req, res) => {
    res.sendFile(__dirname + '/public/views/index.html');
});
app.get('/login', function (req, res) {
    axios.get('http://localhost:8080/api/login', {
        params: {
            userName: req.query.userName,
            password: req.query.password
        },
        headers: {
            Origin: 'http://localhost:5000'
        }
    }).then(function (response) {
        if (response.status === 200) {
            res.status(200).sendFile(__dirname+'/public/views/orderForm.html');
        } else {
            res.send('Invalid User');
        }
    }).catch(function (error) {
        res.send("Invalid User");

    });
});


app.listen(5000, () => {
    console.log('Server is running on http://localhost:5000');
});