# To run training
from time import sleep
from MLtrainingtesting import run_training
## This step can be skipped because it takes a long time to run for higher sample sizes.
## We have saved an existing training output in code/myClassifier.log.
## However, git is known to cause issues when reading files using cPickle. 
## If the next step fails, then come back and run training for a small number like 50.
from compatibility_score import calculate_compatibility
from verifyCompScore import verify_compatibility


def main():
    keep = input("start training?: yes(1) no (0)")
    if int(keep):
        print("start training")
        run_training(200)
    print('Done')
    print()

    print("next step is to calculate compatibility for evaluation")
    keep = input("keep going?: yes(1) no (0)")

    if int(keep):
        print("calculate compatibility(10)")
        calculate_compatibility(50)
    else:
        return

    print("done")
    sleep(1)

    print()
    print("next step is to calculate precision.")
    keep = input("keep going?: yes(1) no (0)")

    if int(keep):
        print("verify_compatibility()")
        verify_compatibility()
    else:
        return

    
if __name__ == "__main__":
    main()


    
        



