import 'dart:io';
import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path_provider/path_provider.dart';

// database table and column names
final String tableEvents = 'events';
final String columnId = '_id';
final String columnEventName = 'eventName';
final String columnEventStartTime = 'eventStartTime';
final String columnEventEndTime = 'eventEndTime';
final String columnLat = 'lat';
final String columnLng = 'lng';
final String columnName = 'name';
final String columnSurname = 'surname';
final String columnTcKimlikNo = 'tcKimlikNo';

// data model class
class Events {
  int id;
  String eventName, eventStartTime, eventEndTime, name, surname, tcKimlikNo;
  double lat, lng;

  Events();
  Events.f(this.eventName,this.eventStartTime,this.eventEndTime,this.name,this.surname,this.tcKimlikNo,this.lat,this.lng);

  // convenience constructor to create a Word object
  Events.fromMap(Map<String, dynamic> map) {
    id = map[columnId];
    eventName = map[columnEventName];
    eventStartTime = map[columnEventStartTime];
    eventEndTime = map [columnEventEndTime];
    lat = map[columnLat];
    lng = map[columnLng];
    name = map[columnName];
    surname = map[columnSurname];
    tcKimlikNo = map[columnTcKimlikNo];
  }

  // convenience method to create a Map from this Word object
  Map<String, dynamic> toMap() {
    var map = <String, dynamic>{
      columnEventName: eventName,
      columnEventStartTime: eventStartTime,
      columnEventEndTime: eventEndTime,
      columnLat: lat,
      columnLng: lng,
      columnName: name,
      columnSurname: surname,
      columnTcKimlikNo: tcKimlikNo
    };
    if (id != null) {
      map[columnId] = id;
    }
    return map;
  }
}

// singleton class to manage the database
class DatabaseHelper {
  // This is the actual database filename that is saved in the docs directory.
  static final _databaseName = "MyDatabase.db";

  // Increment this version when you need to change the schema.
  static final _databaseVersion = 1;

  // Make this a singleton class.
  DatabaseHelper._privateConstructor();

  static final DatabaseHelper instance = DatabaseHelper._privateConstructor();

  // Only allow a single open connection to the database.
  static Database _database;

  Future<Database> get database async {
    if (_database != null) return _database;
    _database = await _initDatabase();
    return _database;
  }

  // open the database
  _initDatabase() async {
    // The path_provider plugin gets the right directory for Android or iOS.
    Directory documentsDirectory = await getApplicationDocumentsDirectory();
    String path = join(documentsDirectory.path, _databaseName);
    // Open the database. Can also add an onUpdate callback parameter.
    return await openDatabase(path,
        version: _databaseVersion, onCreate: _onCreate);
  }

  // SQL string to create the database
  Future _onCreate(Database db, int version) async {
    await db.execute('''
              CREATE TABLE $tableEvents (
                $columnId INTEGER PRIMARY KEY,
                $columnEventName TEXT NOT NULL,
                $columnEventStartTime TEXT NOT NULL,
                $columnEventEndTime TEXT NOT NULL,
                $columnLat DOUBLE NOT NULL,
                $columnLng DOUBLE NOT NULL,
                $columnName TEXT NOT NULL,
                $columnSurname TEXT NOT NULL,
                $columnTcKimlikNo TEXT NOT NULL 
              )
              ''');
  }

  // Database helper methods:

  Future<int> insert(Events eventName) async {
    Database db = await database;
    int id = await db.insert(tableEvents, eventName.toMap());
    return id;
  }

  Future<Events> queryEvent(int id) async {
    Database db = await database;
    List<Map> maps = await db.query(tableEvents,
        columns: [
          columnId,
          columnEventName,
          columnEventStartTime,
          columnEventEndTime,
          columnLat,
          columnLng,
          columnName,
          columnSurname,
          columnTcKimlikNo
        ],
        where: '$columnId = ?',
        whereArgs: [id]);
    if (maps.length > 0) {
      return Events.fromMap(maps.first);
    }
    return null;
  }

  Future<List<Events>> queryAllEvents() async {
    Database db = await database;
    List<Map<String,dynamic>> maps = await db.query(tableEvents);
    List<Events> events = [];
    maps.map((e) => Events.fromMap(e)).toList();
    maps.forEach((element) {
      events.add(Events.f(element['eventName'],element['eventStartTime'],element['eventEndTime'],element['name'],element['surname'],element['tcKimlikNo'],element['lat'],element['lng']));
    });
    return events;
  }
  Future<void> delete(String eventName,String tcKimlikNo) async {
    Database db = await database;
    await db.delete(tableEvents, where: '$columnEventName = ? and $columnTcKimlikNo = ?', whereArgs: [eventName,tcKimlikNo]);
    print('deleted $eventName $tcKimlikNo');
    return null;
  }
// TODO: queryAllWords()
// TODO: delete(int id)
// TODO: update(Word word)
}
