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

The script is written so that risks of additional fields can be calcualted at the same time. Two passes through the data are required:
One for storing the counts, then another for assigning the risk to the output record. If you simply want to create the risk table, only one pass is required.

Note: your risk tables are built off your TRAINING data and applied to training & testing. Do not use your TESTING data when building risk tables.


<b> Spark </b>

This code was built using version 1.3.1 of Spark. It runs map-reduce jobs to calculate fraud counts by category, then calculates smoothed risk at each category level, then prints to stdout.

1) Install Spark & sbt if necessary ( and set $SPARK_HOME)

2) copy simple.sbt and RiskTable.scala to your working directory

3) run "sbt package" to build the jar in ./target/scala-x.xx

4) run "$SPARK_HOME/bin/spark-submit --class RiskTable target/scala-2.10/smoothed-risk_2.10-1.0.jar [SMOOTH]"

Note: the SMOOTH is an optional smoothing parameter. Passing no arguments results in a smoothing parameter = 50.



