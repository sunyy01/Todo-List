package persistence;

import org.json.JSONObject;

// code modelled from JsonSerializationDemo-Thingy
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents s writeable interface
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
