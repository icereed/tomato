SRC=src/
DEST=bin/

JFLAGS=-sourcepath $(SRC) -d $(DEST)
JC=javac

default:
	mkdir -p $(DEST)
	$(JC) $(JFLAGS) $(SRC)tomato/Game.java
	rsync -r $(SRC)tomato/gfx $(DEST)tomato/ --exclude "*.java"
	rsync -r $(SRC)tomato/sound $(DEST)tomato/ --exclude "*.java"

clean:
	rm -r $(DEST)
