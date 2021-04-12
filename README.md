# emotion based song recommender

A simple program to help recommend songs from selected emotion. In this program users can view all songs or emotions in the system, see selected song lyrics, find song from title, add an emotion category, remove song from emotion category and get songs that are related to selected emotion. Also, data used by the program will be written to text files as user exits.

This program counts the scores of each song for each emotion category using occurrence of words in lyrics that belong to the emotion. The song titles and lyrics as well as emotions and words describing those emotions will be supplied using text files. Optionally there can be an additional file provided that prevent songs from being categorized into a specific emotion category.

This project is made for a pair work in Object Oriented Analysis and Design class. Main branch is improvement from the old code in master branch e.g. refactoring, more validation, unit testing.

**Java programming language** is used for development with **JUnit5** as a tool for unit testing. It is recommended to use IntelliJ IDEA to open the project.

## How to run
* open in terminal, go to **src** folder
```sh
# compile
$ javac UI.java
# run
$ java UI songFileName emotionFileName removedSongFileName
```

## Text file format

### songs file

* each song should have format same as below otherwise incorrect results may be produced
```
Song : ......
Lyrics : 
..................................
..................................
..................................
==END==
```



### emotion file
* each emotion data should have format same as below otherwise incorrect results may be produced
* one emotion related word per line is assumed
### emotion file

```
Emotion :  ......
Words :
word1
word2
.....
.....
==END==
```


### removed songs file
* each set of data should have format same as below otherwise incorrect results may be produced
* one song title per line is assumed
```
Emotion : .....
Songs :
song1Title
song2Title
.....
.....
==END==
```
## Demo
### See all songs
<img src="https://user-images.githubusercontent.com/57994731/114345312-abcc8980-9b8b-11eb-801f-9db9847cb6fb.png" width="700" />

### See all emotions
<img src="https://user-images.githubusercontent.com/57994731/114345596-36ad8400-9b8c-11eb-8526-5c67034bef85.png" width="700" />

### See lyrics
<img src="https://user-images.githubusercontent.com/57994731/114345839-a3288300-9b8c-11eb-9d00-f98b5056b03d.png" width="700" />

### Find song from title
<img src="https://user-images.githubusercontent.com/57994731/114346020-f26eb380-9b8c-11eb-99fe-1606a0095524.png" width="700" />

### Find song from emotion
<img src="https://user-images.githubusercontent.com/57994731/114347041-90af4900-9b8e-11eb-93c8-ef3545872253.png" width="700" />

### Add emotion
<img src="https://user-images.githubusercontent.com/57994731/114347081-a0c72880-9b8e-11eb-963c-4a9787b5e65d.png" width="700" />

### Remove song from emotion category
<img src="https://user-images.githubusercontent.com/57994731/114346447-b25c0080-9b8d-11eb-872d-c3c2373659ff.png" width="700" />

<img src="https://user-images.githubusercontent.com/57994731/114346600-f3541500-9b8d-11eb-984c-ebf63fd3a69b.png" width="700" />
