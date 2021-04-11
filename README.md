# emotion based song recommender

A simple program to help recommend songs from selected emotion. In this program users can view all songs or emotions in the system, see selected song lyrics, find song from title, add an emotion category, remove song from emotion category and get songs that are related to selected emotion. Also, data used by the program will be written to text files as user exits.

This program counts the scores of each song for each emotion category using occurrence of words in lyrics that belong to the emotion. The song titles and lyrics as well as emotions and words describing those emotions will be supplied using text file. Optionally there can be an additional file provided that prevent songs from being categorized into a specific emotion category.

## How to run

```sh
# compile
$ javac UI.java
# run
$ java UI songFile.txt emotionFile.txt removedSongFile.txt
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


#### removed songs file
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

