######################
#      Makefile      #
######################

FILE_NAME = documentation.tex

LATEX = xelatex
BIBER = biber
RUSTY_SWAGGER = rusty-swagger

all: clean all1
all1: clean updateproject swagger la la2 la3 
no: clean updateproject swagger la la2 
docker-build: updateproject swagger docker

updateproject:
	mvn -f star-sdk-backend/pom.xml install
	cp star-sdk-backend/starsdk-ws/generated/swagger/swagger.yaml docs/sdk.yaml

swagger:
	cd documentation; $(RUSTY_SWAGGER) --file ../star-sdk-backend/starsdk-ws/generated/swagger/swagger.yaml

la:
	cd documentation;$(LATEX) $(FILE_NAME)
bib:
	cd documentation;$(BIBER) $(FILE_NAME)
la2:
	cd documentation;$(LATEX) $(FILE_NAME)
la3:
	cd documentation;$(LATEX) $(FILE_NAME)
show:
	cd documentation; open $(FILE_NAME).pdf &

docker:
	cp star-sdk-backend/starsdk-ws/target/starsdk-ws-1.0.0-SNAPSHOT.jar ws-sdk/ws/bin/starsdk-ws-1.0.0.jar
	docker build -t 979586646521.dkr.ecr.eu-west-1.amazonaws.com/ubiquevscovid19-ws:test ws-sdk/
	


clean:
	@rm -f documentation/*.log documentation/*.aux documentation/*.dvi documentation/*.ps documentation/*.blg documentation/*.bbl documentation/*.out documentation/*.bcf documentation/*.run.xml documentation/*.fdb_latexmk documentation/*.fls documentation/*.toc