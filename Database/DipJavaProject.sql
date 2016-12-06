DROP TABLE IF EXISTS menu;
CREATE TABLE  menu (
  itemID int(10),
  name varchar(45),
  category varchar(45),
  price decimal(10,0),
  PRIMARY KEY (itemID)
);
DROP TABLE IF EXISTS restaurantTables;
CREATE TABLE  restaurantTables (
  tableNumber int(10),
  subTotal decimal(10,0),
  waitername varchar(45),
  PRIMARY KEY (tableNumber)
);
DROP TABLE IF EXISTS waiters;
CREATE TABLE  waiters (
  WaiterName varchar(45),
  PRIMARY KEY (WaiterName)
);

INSERT INTO menu VALUES(1,"Minerals","Beverage",2);
INSERT INTO menu VALUES(2,"Tea","Beverage",2);
INSERT INTO menu VALUES(3,"Coffee","Beverage",1);
INSERT INTO menu VALUES(4,"Mineral Water","Beverage",3);
INSERT INTO menu VALUES(5,"Fruit Juice","Beverage",2);
INSERT INTO menu VALUES(6,"Milk","Beverage",2);
INSERT INTO menu VALUES(7,"Chicken Wings","Appetizer",6);
INSERT INTO menu VALUES(8,"Pate and Toast","Appetizer",7);
INSERT INTO menu VALUES(9,"Potato Skins","Appetizer",9);
INSERT INTO menu VALUES(10,"Nachos","Appetizer",9);
INSERT INTO menu VALUES(11,"Garlic Mushrooms","Appetizer",11);
INSERT INTO menu VALUES(12,"Seafood Cocktail","Appetizer",13);
INSERT INTO menu VALUES(13,"Brie Cheese","Appetizer",7);
INSERT INTO menu VALUES(14,"Seafood Alfredo","Main Course",16);
INSERT INTO menu VALUES(15,"Chicken Alfredo","Main Course",14);
INSERT INTO menu VALUES(16,"Lasagne","Main Course",14);
INSERT INTO menu VALUES(17,"Turkey Club","Main Course",12);
INSERT INTO menu VALUES(18,"Lobster Pie","Main Course",20);
INSERT INTO menu VALUES(19,"Rib Steak","Main Course",21);
INSERT INTO menu VALUES(20,"Scampi","Main Course",19);
INSERT INTO menu VALUES(21,"Turkey & Ham","Main Course",14);
INSERT INTO menu VALUES(22,"Chicken Kiev","Main Course",15);
INSERT INTO menu VALUES(23,"Apple Pie","Dessert",6);
INSERT INTO menu VALUES(24,"Sundae","Dessert",4);
INSERT INTO menu VALUES(25,"Carrot Cake","Dessert",6);
INSERT INTO menu VALUES(26,"Mud Pie","Dessert",5);
INSERT INTO menu VALUES(27,"Pavlova","Dessert",6);
INSERT INTO menu VALUES(28,"<<NONE>>","Beverage",0);
INSERT INTO menu VALUES(29,"<<NONE>>","Appetizer",0);
INSERT INTO menu VALUES(30,"<<NONE>>","Main Course",0);
INSERT INTO menu VALUES(31,"<<NONE>>","Dessert",0);

INSERT INTO restaurantTables VALUES(1,0,"Jon");
INSERT INTO restaurantTables VALUES(2,0,"Jon");
INSERT INTO restaurantTables VALUES(3,0,"Jon");
INSERT INTO restaurantTables VALUES(4,0,"Jon");
INSERT INTO restaurantTables VALUES(5,0,"Jon");
INSERT INTO restaurantTables VALUES(6,0,"Sue");
INSERT INTO restaurantTables VALUES(7,0,"Sue");
INSERT INTO restaurantTables VALUES(8,0,"Sue");
INSERT INTO restaurantTables VALUES(9,0,"Sue");
INSERT INTO restaurantTables VALUES(10,0,"Sue");

INSERT INTO waiters VALUES("Jon");
INSERT INTO waiters VALUES("Sue");
