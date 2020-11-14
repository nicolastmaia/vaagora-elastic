
CREATE VIEW  UserLocation as SELECT users.id, users.name, latitude, longitude, created, modified FROM users, current_loc where users.location_id = current_loc.id
