#!/usr/bin/python
import sys

### create dictionaries to hold counts, default smooth = 50 ###
data={}
f1={}
count=0
counts=[0,0]
try: smooth = int(sys.argv[1])
except: smooth = 50

##### from STDIN get nonfraud,fraud counts by category ####
for line in sys.stdin:
	row = line.strip().split(',')
	data[count]=row
	count+=1
	counts[int(row[0])]+=1
	if row[1] not in f1: f1[row[1]]=[0,0]
	f1[row[1]][int(row[0])]+=1

##### return risk table for one variable ####
def risk(frauds,fields,smooth,verbose=1):
  table={}
  for a in fields:
	table[a]=(float(fields[a][1])+smooth*(float(frauds[1])/sum(frauds)))/sum(fields[a],smooth)/(float(frauds[1])/sum(frauds))
        if verbose==1: print a,fields[a],table[a]
  return table

f1_risk = risk(counts,f1,smooth,verbose=0)

### print input data to STDOUT with smoothed risk: new categories get risk=1.0 ####
for a in data:
  print "%s,%s," % (data[a][0],data[a][1]),
  if data[a][1] in f1_risk: print "%.3f" % f1_risk[data[a][1]]
  else: print "1.000" 
