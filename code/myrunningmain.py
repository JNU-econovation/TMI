#Take code from MLtrainingtesting.py

from MLtrainingtesting import run_training

## DO NOT RUN TRAINING. IT TAKES FOREVER. 
## WE RAN THE TRAINING AND SAVED THE OUTPUT TO BE USED IN OTHER PLACES.
print("Running run_training function now")
run_training(500,2)
#print 'Done'

#temp= raw_input('Take a moment to appreciate, press random key to continue')
#--------------------------------------------------------------------------------
#%% To run the compatibility score algorithm

from compatibility_score import calculate_compatibility
print("Running calculate_compatibility function now")
calculate_compatibility(50)
#temp= raw_input('Take a moment to appreciate, press random key to continue')

#--------------------------------------------------------------------------------

#%% To calculate precision
from verifyCompScore import verify_compatibility
print("Running verify_compatibility function now")
verify_compatibility()
