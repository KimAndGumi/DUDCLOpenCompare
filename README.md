# Getting started with OpenCompare

Ambition :

Java procedure that is able to generate a 4D graph (bullet chart) from a PCM file (Product Comparison Matrix) or CSV or JSON file using Plot.ly or NVD3 library.




How to use it :
use wizard mode ; or entry parameters are to be written as follow :

$java -jar [jar name] [file path/name] x=[chosen column*] y=[second chosen column*] color=[third chosen column*] size=[fourth chosen column*]  l=[Plot.ly or Nvd3]

*The user has to know the data structure, so that each chosen column is of numerical type (int or float).

What it generates :

An index.html file is then generated. The data graph is shown with data features' list.


Note finale :

This project was possible with the work of teamDudcl.