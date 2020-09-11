# makefile pro preklad LaTeX verze Bc. prace
# makefile for compilation of the thesis
# (c) 2008 Michal Bidlo
# E-mail: bidlom AT fit vutbr cz
# Edited by: dytrych AT fit vutbr cz
#===========================================
# asi budete chtit prejmenovat / you will probably rename:
CO=xsipos03-proc-gen-dungeonu

all: $(CO).pdf

pdf: $(CO).pdf

$(CO).ps: $(CO).dvi
	dvips $(CO)

$(CO).pdf: clean
	pdflatex $(CO)
	-bibtex $(CO)
	pdflatex $(CO)
	pdflatex $(CO)

$(CO).dvi: $(CO).tex $(CO).bib
	latex $(CO)
	-bibtex $(CO)
	latex $(CO)
	latex $(CO)

clean:
	rm -f *.dvi *.log $(CO).blg $(CO).bbl $(CO).toc *.aux $(CO).out $(CO).lof $(CO).ptc
	rm -f $(CO).pdf
	rm -f *~

pack:
	tar czvf $(CO).tar.gz *.tex *.bib *.cls ./template-fig/* ./obrazky/* ./kody/* ./bib-styles/* zadani.pdf $(CO).pdf Makefile

rename:
	mv $(CO).tex $(NAME).tex
	mv $(CO)-kapitoly.tex $(NAME)-kapitoly.tex
	mv $(CO)-literatura.bib $(NAME)-literatura.bib
	mv $(CO)-prilohy.tex $(NAME)-prilohy.tex
	sed -i "s/$(CO)-kapitoly/$(NAME)-kapitoly/g" $(NAME).tex
	sed -i "s/$(CO)-literatura/$(NAME)-literatura/g" $(NAME).tex
	sed -i "s/$(CO)-prilohy/$(NAME)-prilohy/g" $(NAME).tex
	sed -i "s/$(CO)/$(NAME)/g" Makefile
	
# Pozor, vlna neresi vse (viz popis.txt) / Warning - vlna is not solving all problems (see description.txt)
vlna:
	vlna -l $(CO)-*.tex

# Spocita normostrany / Count of standard pages
normostrany:
	echo "scale=2; `detex -n $(CO)-kapitoly.tex | wc -c`/1800;" | bc

