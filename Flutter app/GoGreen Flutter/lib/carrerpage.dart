import 'dart:async';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:charts_flutter/flutter.dart' as charts;
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

class careerpage extends StatefulWidget {
  final String userEmail;

  const careerpage(this.userEmail, {Key key}) : super(key: key);

  @override
  _careerPageState createState() => new _careerPageState();
}

class _careerPageState extends State<careerpage> {
  // class fields
  List data;
  List<charts.Series<pieClass, String>> _seriesPieData;
  double totalCo2Saved;
  double totalMoneySaved;
  double totalEnergySaved;
  double foodCo2Saved;
  double transportCo2Saved;
  double solarCo2Saved;
  double tempCo2Saved;
  double foodMoneySaved;
  double transportMoneySaved;
  double solarMoneySaved;
  double tempMoneySaved;

  _generatePieData() {
    var pieData = [
      new pieClass("Food", foodCo2Saved, Color(0xff3366cc)),
      new pieClass("transport", transportCo2Saved, Color(0xff990099)),
      new pieClass("Energy", tempCo2Saved, Color(0xffdc3912))
    ];

    _seriesPieData.add(
        charts.Series(
          data: pieData,
          domainFn: (pieClass pieclass, _) => pieclass.name,
          measureFn: (pieClass pieclass, _) => pieclass.value,
          colorFn: (pieClass pieclass, _) =>
              charts.ColorUtil.fromDartColor(pieclass.colorvalue),
          id: 'Co2 savings breakdown',
          labelAccessorFn: (pieClass row, _) => '${row.name}: ${row.value}',
        )
    );
  }

  // Page initialization method to get the users email.
  @override
  void initState() {
    super.initState();
    getUserCareer();
  }

  // Function to get UserCareer data
  Future<String> getUserCareer() async {
    String url = 'https://gogreen82-server.herokuapp.com/api/usercareer/' +
        widget.userEmail;
    print(url);

    http.Response response = await http
        .get(Uri.encodeFull(url), headers: {"Accept": "Application/json"});
    print(response.body);

    this.setState(() {
      data = json.decode(response.body.toString());
      totalCo2Saved = data[0]["totalCo2Saved"];
      totalMoneySaved = data[0]["totalMoneySaved"];
      totalEnergySaved = data[0]["totalEnergySaved"];
      foodCo2Saved = data[0]["foodCo2Saved"];
      transportCo2Saved = data[0]["transportCo2Saved"];
      solarCo2Saved = data[0]["solarCo2Saved"];
      tempCo2Saved = data[0]["tempCo2Saved"];
      foodMoneySaved = data[0]["foodMoneySaved"];
      transportMoneySaved = data[0]["transportMoneySaved"];
      solarMoneySaved = data[0]["solarMoneySaved"];
      tempMoneySaved = data[0]["tempMoneySaved"];
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: DefaultTabController(
          length: 3,
          child: Scaffold(
            appBar: AppBar(
              backgroundColor: Color(0xff1976d2),
              bottom: TabBar(
                indicatorColor: Color(0xff9962D0),
                tabs: [
                  Tab(icon: Icon(FontAwesomeIcons.chartPie)),
                  Tab(icon: Icon(FontAwesomeIcons.chartPie)),
                  Tab(icon: Icon(FontAwesomeIcons.chartPie)),
                ],
              ),
              title: Text('Flutter Charts'),
            ),
            body: TabBarView(
              children: [
                Padding(
                  padding: EdgeInsets.all(8.0),
                  child: Container(
                    child: Center(
                      child: Column(
                        children: <Widget>[
                          Text(
                            'Total co2 savings', style: TextStyle(
                              fontSize: 24.0, fontWeight: FontWeight.bold),),
                          SizedBox(height: 10.0,),
                          Expanded(
                            child: charts.PieChart(
                                _seriesPieData,
                                animate: true,
                                animationDuration: Duration(seconds: 5),
                                behaviors: [
                                  new charts.DatumLegend(
                                    outsideJustification: charts
                                        .OutsideJustification.endDrawArea,
                                    horizontalFirst: false,
                                    desiredMaxRows: 2,
                                    cellPadding: new EdgeInsets.only(
                                        right: 4.0, bottom: 4.0),
                                    entryTextStyle: charts.TextStyleSpec(
                                        color: charts.MaterialPalette.purple
                                            .shadeDefault,
                                        fontFamily: 'Georgia',
                                        fontSize: 11
                                    ),
                                  )
                                ],

                                defaultRenderer: new charts.ArcRendererConfig(
                                    arcRendererDecorators: [
                                      new charts.ArcLabelDecorator(
                                          labelPosition: charts.ArcLabelPosition
                                              .outside)
                                    ])


                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                )
              ],
            ),
          )),
    );
  }
}


class pieClass {
  String name;
  double value;
  Color colorvalue;

  pieClass(this.name, this.value, this.colorvalue);
}
