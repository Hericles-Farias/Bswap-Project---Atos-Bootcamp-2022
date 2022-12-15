
![Logo](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/th5xamgrr6se0x5ro4g6.png)



# BSWP - Swap and Go
This is a scheduling application applied in a Battery Swapping Station (BSS).
The main idea is, through scheduling, to provide a way for the BSS to know
the day ahead battery demand, so that the station can optimize the recharge
of depleted batteries in order to increase battery availability.



## Overview

![Logo](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/th5xamgrr6se0x5ro4g6.png)

A BSS consists in a station where people can swap their depleted batteries (low state of charge)
for a fully charged one in order to continue their trips. 

For the user this represents an option much more faster than the usual Recharging Station, where
the user must wait untill the recharge is completed (ranging from 30min to more than 10 hours, depending on
the connector type)! A BSS takes only a few minutes (usually less than 5) to complete the swap!

For the BSS, although the swapping process is quite fast, the depleted batteries still need
to be recharged, and this operation can take some time to be done. However, instead of 
making the user also wait, this operations independs of the user and can be deferred 
to another period (if this make economic or technical sense).

In order to improve the deplete batteries charging strategy the BSS must know in
advance the demand for batteries in the next hours so it can optimize the charging time
of each empty battery, so when the user comes there is always a battery available for swapping!


### Problem to be solved
Charging time of each depleted battery.

### Solution provided
Use of scheduling to know in advance the day ahead battery demand


## Stack utilizada

**Front-end:** HTML5, Javascript, CSS3

**Back-end:** Java, MySQL

**Framework**: Spring Boot

**Depêndencias**:
* Spring Web
* Spring Security
* Spring Data JPA 
* Thymeleaf
* Spring Data Validation
* MySQL Driver
* Lombok
* Mapstruct


## Running Locally
Make sure you have XAMP installed (or another program similar), we are going to use it to host
our SQL database, in this project I have called my databse as bssdb, but you can change its name 
by overriding the name that is inside of the application.properties file! The database initial setup is all there!

Clone the project

```bash
  git clone https://github.com/Hericles-Farias/Bswap.git
```

Unpack it and import in and IDE of your choice. I developed it in VSCode.

Inside the package com.br.atos2022.bswap.common there is a class called DataLoader.java. The first time 
you run the code this class performs the initial writings in the database (creation of roles, supported batteries and plan).
This class should be run just at startup, so after it you can just comment out all the codes inside this java file, otherwise the code
may complain about!

The project is running in localhost on port 8080!




## Application documentation

All endpoints returns an HTML view due to Thymeleaf-Engine!


#### Returns the BSWAP landing page

```http
  GET /bswap
```

#### Returns the admin home page

```http
  GET /bswap/adminHomePage
```

#### Returns the user home page

```http
  GET /bswap/userHomePage
````

#### Returns the login page

```http
  GET /bswap/login
````

#### Returns the register page

```http
  GET /bswap/register
````

#### Returns a Forbidden Page Warn

```http
  GET /bswap/Forbiden
````

#### Returns a user profile

```http
  GET /bswap/myProfile/{id}
````


| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `integer` | **Mandatory**. The ID of the user |


#### Adds an EV objet to an user

```http
  POST /bswap/myProfile/{id}/addEV
````


| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `integer` | **Mandatory**. The ID of the user |

#### Changes the password of an user

```http
  POST /bswap/myProfile/{id}/changePassword
````


| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `integer` | **Mandatory**. The ID of the user |

#### Changes the plan of an user

```http
  GET /bswap/myProfile/{id}/changeToPlan/{name}
````


| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `integer` | **Mandatory**. The ID of the user |
| `name`      | `string` | **Mandatory**. The name of the plan |

#### Removes a battery from an user

```http
  GET /bswap/myProfile/{id}/removeBattery/{battID}
````


| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `integer` | **Mandatory**. The ID of the user |
| `battID`      | `integer` | **Mandatory**. The ID of the battery |

#### Removes an EV from an user

```http
  GET /bswap/myProfile/{id}/removeEV/{plate}
````


| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `integer` | **Mandatory**. The ID of the user |
| `plate`      | `string` | **Mandatory**. The plate of the EV |

#### Removes a battery from an user

```http
  GET /bswap/myProfile/{id}/removeBattery/{battID}
````


| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `integer` | **Mandatory**. The ID of the user |
| `battID`      | `integer` | **Mandatory**. The ID of battery |

#### Returns the EV Form to add a new type

```http
  GET /bswap/myProfile/{id}/showEVForm
````

#### Returns the Password Form to change to a new one

```http
  GET /bswap/myProfile/{id}/showPasswordForm
````

#### Returns the Plan Selection Page

```http
  GET /bswap/myProfile/{id}/showPlanSelection
````

#### Returns the Schedulings of an user

```http
  GET /bswap/mySchedulings/{id}
````

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `integer` | **Mandatory**. The ID of the user |

#### Adds a scheduling to an user

```http
  POST /bswap/mySchedulings/{id}/addScheduling
````

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `integer` | **Mandatory**. The ID of the user |

#### Cancells a scheduling from an user

```http
  PUT /bswap/mySchedulings/{id}/cancellSched/{schedID}
````

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `integer` | **Mandatory**. The ID of the user |
| `schedID`      | `integer` | **Mandatory**. The ID of the scheduling |

#### Shows the scheduling Form

```http
  GET /bswap/mySchedulings/{id}/showSchedForm
````

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `integer` | **Mandatory**. The ID of the user |

#### Returns Batteries Page

```http
  GET /bswap/batteries
````

#### Deletes a battery from the BSS catalog

```http
  GET /bswap/batteries/{battID}/delete
````

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `battID`      | `integer` | **Mandatory**. The ID of the battery |

#### Deletes a battery from the BSS catalog

```http
  GET /bswap/batteries/addBattery
````

#### Shows the battery Form

```http
  GET /bswap/batteries/showBattForm
````

#### Shows the Day Ahead Operation

```http
  GET /bswap/batteries/dayAhead
````

#### Shows plans Page

```http
  GET /bswap/plans
````

#### Deletes a plan from the BSS catalog

```http
  GET /bswap/plans/{name}/delete
````

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `name`      | `string` | **Mandatory**. The name of the plan |

#### Adds a new plan to the BSS catalog

```http
  POST /bswap/plans/addPlan
````

#### Show the Plan Form

```http
  GET /bswap/plans/showPlanForm
````

#### Registers an user

```http
  POST /bswap/register
````

#### Shows all registered users

```http
  GET /bswap/registeredUsers
````

#### Shows all users schedulings

```http
  GET /bswap/userSchedulings
````

#### General Error Page

```http
  GET /error
````




## Presentation Video
You can see this project working here!
## Autores

- [Héricles Eduardo Oliveira Farias](https://www.github.com/Hericles-Farias)


## Feedback

If you have any feedback, you can contact me at hericleslannister@gmail.com

