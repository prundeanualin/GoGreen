import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';
import 'package:app_profilepage/dialogs.dart';

class veggieMealPage extends StatefulWidget {
  @override
  _veggieMealPageState createState() => new _veggieMealPageState();
}

class _veggieMealPageState extends State<veggieMealPage> {

  // data to send to the server
  String _food1 = '';
  int _price1 = 0;
  String _food2 = '';
  int _price2 = 0;
  String _email = '';
  List<DropdownMenuItem<String>> _foods(){
    List<String> ddl = ["beef", "eggs", "fruit", "milk"];
    return ddl.map(
            (value) => new DropdownMenuItem<String>(
          child: Text(value),
          value: value,
        )
    ).toList();
  }
  Dialogs dialogs = new Dialogs();

  // result data
  double _resultCo2 = 0;
  double _resultMoney = 0;

  // Page initialization method to get the users email.
  void initState() {
    super.initState();
    FirebaseAuth.instance.currentUser().then((user) {
      setState(() {
        _email = user.email;
      });
    }).catchError((e) {
      print(e);
    });
  }


  // DEMO Function to send HTTP request and get/post data
  Future<String> getDataDemo() async {
    String url = 'https://randomuser.me/api/';
    http.Response response = await http.get(Uri.encodeFull(url), headers: {"Accept": "application/json"});

    List data;
    var extractData = json.decode(response.body);
    data = extractData["results"];

    print(data[0]["name"]["first"]);
  }

  // Function to send HTTP request and get/post data
  Future<String> getData() async{
    String url1 = 'https://gogreen82-server.herokuapp.com/api/';
    String url= url1 + _email +"/"+ _food1 +"/"+ _price1.toString() +"/"+ _food2 +"/"+ _price2.toString();

    http.Response response = await http.get(Uri.encodeFull(url), headers: {"Accept" : "Application/json"});
    print(response.body);

    var extractData = json.decode(response.body);
    _resultCo2 = extractData["resultCo2"];
    _resultMoney = extractData["resultMoney"];
    print(_resultCo2);
    print(_resultMoney);

    dialogs.information(context, "test", _resultCo2, _resultMoney);

  }



  @override
  Widget build(BuildContext context) {
    return Scaffold(
      floatingActionButton: FloatingActionButton.extended(
          onPressed: getData,
          icon: Icon(Icons.done),
          label: Text('Save')),
      appBar: AppBar(
          centerTitle: true,
          title: Text('Enter Food details')),
      body: Container(
        padding: EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[

            Center(
              child: DropdownButton(
                value: _food1 == "" ? null : _food1,
                items: _foods(),
                onChanged: (String value) {
                  _food1 = value;
                  setState(() {
                  });
                },
                hint: Text("Food that you usually take?"),
              ),
            ),

//            ListTile(
//              title: TextField(
//                onChanged: (value) {
//                  _food1 = value;
//                },
//                decoration: InputDecoration(
//                    icon: Icon(Icons.fastfood),
//                    labelText: 'Food that you usually eat?',
//                    labelStyle: TextStyle(fontWeight: FontWeight.bold)),
//                maxLines: 1,
//              ),
//            ),

            TextField(
              onChanged: (value) {
                _price1 = int.parse(value);
              },
              keyboardType: TextInputType.number,
              decoration: InputDecoration(
                  icon: Icon(Icons.euro_symbol),
                  labelText: 'Price of the food you usually eat?',
                  labelStyle: TextStyle(fontWeight: FontWeight.bold)),
            ),

            Center(
              child: DropdownButton(
                value: _food2 == "" ? null : _food2,
                items: _foods(),
                onChanged: (String value) {
                  _food2 = value;
                  setState(() {
                  });
                },
                hint: Text("Food that ate instead?"),
              ),
            ),


//            TextField(
//              onChanged: (value) {
//                _food2 = value;
//              },
//              decoration: InputDecoration(
//                icon: Icon(Icons.local_dining),
//                labelText: 'Food that you ate instead?',
//                labelStyle: TextStyle(fontWeight: FontWeight.bold),
//              ),
//            ),



            TextField(
              keyboardType: TextInputType.number,
              onChanged: (value) {
                _price2 = int.parse(value);
              },
              decoration: InputDecoration(
                  icon: Icon(Icons.euro_symbol),
                  labelText: 'Price of new food?',
                  labelStyle: TextStyle(fontWeight: FontWeight.bold)),
            )
          ],
        ),
      ),
    );
  }
}
