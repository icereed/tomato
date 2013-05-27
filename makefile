SRC=src/
DEST=bin/

JFLAGS=-sourcepath $(SRC) -d $(DEST)
JC=javac

default: $(SRC)tomato/Game.java
	$(JC) $(JFLAGS) $(SRC)tomato/Game.java

clean:
	rm -r $(DEST)*
