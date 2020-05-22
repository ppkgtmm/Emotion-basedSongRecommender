# emotion based song recommender

## How to run

```java
// compile
> javac UI.java
// run
> java UI songFile.txt emotionFile.txt removedSongFile.txt
```



## Text file format

#### songs file

```
Song :  song title
Lyrics : 
..................................
..................................
..................................
=ENDSONG=
```



#### emotion file

```
emotion :  [
word1
word2
....... ]
```

or

```
emotion :  [
word1
word2
...........
]
```



#### removed songs file

```
emotion : [
song title
song title
............
]
```

