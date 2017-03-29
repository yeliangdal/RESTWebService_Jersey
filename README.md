# RackSpaceWebService
A server side Restful service. 
It was designed and buit for a Cloud Computing course project

## Built with
* jersey
* maven
* mysql
* cloudinary

## What does it do
1. Provide RESTful services that allow user to create/modify/delete entries in a MySQL database
2. Integrated with cloudinary photo storage service

## How to use it 
1. Download the repo
```
git clone https://github.com/yeliangdal/RackSpaceWebService.git
```
2. import the project to your IDE

3. Change api key and api secret in /src/com/ye/rackspace/CloudinaryDelete.java and /src/com/ye/rackspace/CloudinaryUpload.java

4. Change MySQL settings in /src/com/ye/rackspace/DBConnetctor.java

5. Create 2 tables in MySQL DB
```
CREATE TABLE IF NOT EXISTS `Photos` (
  `studentID` int(3) NOT NULL,
  `photoID` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`photoID`),
  UNIQUE KEY `photoID` (`photoID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
```
```
CREATE TABLE IF NOT EXISTS `Students` (
  `studentID` int(11) NOT NULL,
  `nickname` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`studentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
```
6. Compile and deploy war file in web server
