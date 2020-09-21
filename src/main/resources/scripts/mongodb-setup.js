mongo -u user_root -p root admin

use luiza_db

db.createUser({user: "luizalabs_user", pwd: "SSDW/Thtywtu9vDYl0T9WHk9Ujze16WH0LUD9l+/1nuJXyOmMCG/KeZzmtoXoxL9BZ8MVgWf5ZEqj4bg", roles: [{ role: "readWrite", db: "luiza_db" }],mechanisms: ["SCRAM-SHA-1"]});

db.createCollection("clientes")

