######################
#      Makefile      #
######################

FILE_NAME = documentation.tex

LATEX = xelatex
BIBER = biber
RUSTY_SWAGGER = rusty-swagger

all: clean all1
all1: clean swagger la la2 la3 
no: clean swagger la la2 

swagger:
	@$(RUSTY_SWAGGER) --file ../star-sdk-backend/starsdk-ws/generated/swagger/swagger.yaml

la:
	@$(LATEX) $(FILE_NAME)
bib:
	@$(BIBER) $(FILE_NAME)
la2:
	@$(LATEX) $(FILE_NAME)
la3:
	@$(LATEX) $(FILE_NAME)
show:
	@open $(FILE_NAME).pdf &


clean:
	@rm -f *.log *.aux *.dvi *.ps *.blg *.bbl *.out *.bcf *.run.xml *.fdb_latexmk *.fls