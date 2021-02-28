# Lyric Master

A Clojure application designed to help songwriters improve their lyrics through data analysis of successful songs:

## Running locally

### Prerequisites

Developed using:
- Java 12.0.1
- Leiningen 2.8.1

More instructions may follow as app progresses.


## Usage

Features include parsing and analyzing songs.\
Original design is for hip hop lyric analysis. 

### Parsing text files

Parses songs from strings. The frontend UI provides a text field to paste lyrics for analysis.
Songs can also be parsed from`.txt` files in `/resources` folder using `lyric-backend.file-io.clj`'s `file->str`
 function (repl users). 

Can parse multiple songs from one file / string.

##### Parsing rules

Songs are parsed with the following rules:

- `NEW SONG` starts a new song
- Optional domain specific commands exist to define arbitrary information about each song:
    - usage: `COMMAND "value"`
    - E.g. `TITLE "title"` sets song title. parameter `"title"` must be inside quotes and cannot contain whitespaces or special characters.
    - Available commands: `ARTIST, TITLE`
- Words within brackets `()` & `[]` are ignored (background noise or info).
- Each new line starts a new bar. 
    - A bar means roughly "one line in a poem (or song)"
    
##### Output
`````
:title String,
:artist String,
:song {:lyrics String,
       :bars Array<String>,
       :words Arrray<String>,
`````


##### Example

parsing

````
TITLE "My Name Is"
ARTIST "Eminem"

Hi, kids, do you like violence? (Yeah, yeah, yeah)
Wanna see me stick Nine Inch Nails through each one of my eyelids? (Uh-huh)  

(...)


NEW SONG 

TITLE "Guilty Conscience"
         
Alright, stop (Huh?)
Now before you walk in the door of this liquor store
And try to get money out the drawer   

(...)

````

outputs

`````
({:title "My Name Is",
  :artist "Eminem",
  :song {:lyrics "Hi, kids, do you like violence? \nWanna see...",
         :bars ["Hi, kids, do you like violence? " 
                "Wanna see me stick Nine Inch Nails through each one of my eyelids?"],
         :words ("hi"
                 "kids"
                 "do"
                 "you"...)}}
 {:title "Guilty Conscience",
  :artist nil,
  :song {:lyrics "Alright, stop now...",
         :bars ["Alright, stop "
                "Now before you walk..."],
         :words ("alright"
                 "stop"...)}})
`````

### Analysis metrics

current metrics (produced by `lyric-backend.song-analyzer.clj`)

| Metric                  | Type          | Legend  |
| -------------           |:-------------:| -----:|
| :bar-count              | integer       |  |
| :bar-count              | integer       |    |
| :word-count             | integer       |     |
| :rhyme-count            | integer       |       |
| :distinct-word-count    | integer       |       |
| :distinct-rhyme-count   | integer       |       |
| :distinct-rhyming-words | vector        |       |
| :rhymes-per-bar         | decimal       |ratio of distinct rhyming words and bars       |
| :rhymes-per-word        | decimal       |ratio of distinct rhyming words and all words       |
| :word-usage-amounts     | map(integer->vector) | words in a vector v appear in the song n times where n is the key to v in the map      | 

## Author & License

Author\
Evan Miller, 2021

Distributed under the Eclipse Public License version 1.0
