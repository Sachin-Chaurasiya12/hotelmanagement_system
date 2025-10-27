import configparser;
import json;
config = configparser.ConfigParser()
config.read("config.ini")

dbhost = config["database"]["host"]
dbport = config["database"]["port"]
dbuser = config["database"]["user"]
dbpass = config["database"]["password"]

data = {
    "database" : {
        "host" : dbhost,
        "port" : dbport,
        "user" : dbuser,
        "pass" : dbpass
    }
}

print(json.dumps(data))