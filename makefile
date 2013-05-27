SRC=src/
DEST=bin/

JFLAGS=-sourcepath $(SRC) -d $(DEST)
JC=javac

default:
	mkdir -p $(DEST)
	$(JC) $(JFLAGS) $(SRC)tomato/Game.java

clean:
	rm -r $(DEST)
