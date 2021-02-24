# lyric-master

A Clojure application designed to help songwriters improve their lyrics through data analysis of successful songs:

## Usage

Original design is for hip hop lyric analysis

### Metrics

Planned initial features
- matching rhymes
- counting bars

### Parsing text files

Parses songs from `.txt` files in `/resources` folder which is found at project root.

Songs are parsed with the following rules:

- `NEW SONG "title"` starts a new song with give title.
- Words within brackets `()` & `[]` are ignored (background noise or info).
- Each new line starts a new "bar". 
    - A bar means roughly "one line in a poem (or song)"


example.txt 
````
NEW SONG "My Name Is"

[Eminem & Dr. Dre:]
Hi, kids, do you like violence? (Yeah, yeah, yeah)
Wanna see me stick Nine Inch Nails through each one of my eyelids? (Uh-huh)  

(...)


NEW SONG "Guilty Conscience" (feat. Dr. Dre)
         
Alright, stop (Huh?)
Now before you walk in the door of this liquor store
And try to get money out the drawer   

(...)

````

### Prerequisites

- Java 12.0.1
- Leiningen 2.8.1

## License

Copyright © 2021 Evan Miller

Distributed under the Eclipse Public License either version 1.0
