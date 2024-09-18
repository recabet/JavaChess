src := $(shell find chess/ -name '*.java')
obj = $(src:.java=.class)

all: chess

chess: $(obj)

%.class: %.java
	javac $<

run: chess
	java chess.Main

clean:
	find chess/ -name '*.class' | xargs rm -f

.PHONY: clean run
