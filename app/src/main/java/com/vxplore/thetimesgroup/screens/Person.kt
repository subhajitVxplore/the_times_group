package com.vxplore.thetimesgroup.screens

data class Person(
    val name: String,
    val age: Int,
    val profilePictureUrl: String? = null
)


fun getPersonList() = listOf(
    Person("Grace Hopper", 25),
    Person("Ada Lovelace", 29),
    Person("John Smith", 28),
    Person("Elon Musk", 41),
    Person("Will Smith", 31),
    Person("Robert James", 42),
    Person("Anthony Curry", 91),
    Person("Kevin Jackson", 22),
    Person("Robert Curry", 1),
    Person("John Curry", 9),
    Person("Ada Jackson", 2),
    Person("Joe Defoe", 35)
)

fun getPersonAge() = listOf(
    Person("Grace Hopper", 50),
    Person("Ada Lovelace", 100),
    Person("John Smith", 150),
    Person("Elon Musk", 200),
    Person("Will Smith", 250),
    Person("Robert James", 300),
)
data class Paper(
    val name: String,
    val price: Int,
  )
fun getPaperPrice() = listOf(
    Paper("Times of India", 5),
//    Paper("Economics Times", 7),
//    Paper("Ei Samay", 3),
//    Paper("Ananda Bazar", 2),
//    Paper("Bortoman", 2),
//    Paper("The Telegraph", 5),
//    Paper("Hindustan Times", 5),
//    Paper("The Indian Express", 7),
//    Paper("The Hindu", 4),
//    Paper("Dainik Jagran", 2),
//    Paper("Dainik Bhaskar", 2),

)
