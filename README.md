# RiskTables
Generate smoothed risk tables from a csv with a python script

Sample data = input.csv

Format = fraud/nonfraud (1,0 respectively), category (ranges from 0-4)

Script = risk.py - reads CSV data from STDIN and writes data to STDOUT with risk appended. 

Usage = <b> > ./risk.py SMOOTH < input.csv </b>, where SMOOTH is optional smoothing parameter (default=50). 
Higher smoothing parameters will pull the risks of small categories closer to zero.
Try setting your parameter to the lowest number of observations in a category where you want the smoothing to be effective.
At SMOOTH=0, the table defaults to a simple risk.

In the script, there is a verbose flag set =0. Change this to 1 if you want to display the risk table values.

The script is written so that risks of additional fields can be calcualted at the same time. Two passes through the data are required. 
One for storing the counts, then another for assigning the risk to the output record. If you simply want to create the risk table, only one pass is required.

Note: your risk tables are built off your TRAINING data and applied to training & testing. Do not use your TESTING data when building risk tables.
