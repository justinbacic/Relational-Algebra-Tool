# Relational-Algebra-Tool
This tool is to help with Relational Algebra Queries, made for COMP 3005
Here is a link to the video demonstration of the system: https://youtu.be/qFRK5gsXFfw

Syntax Guide:
Selection: use keyword select for selection, use >,<,>=,<= and = for conditionals. Eg. select Column>x(Relation)"
Projection: use keyword project, separate desired columns by commas. Eg. project column1,column2(Relation)"
Join: use keyword join for regular join, rjoin for right outer join, ljoin for left outer join and fjoin for full join. Eg. (Relation1)rjoin Relation1.column=Relation2.column(Relation2)"
Set Operators: Use n for intersection, use u for union and - for minus. Eg. (Relation1)n(Relation2)"
