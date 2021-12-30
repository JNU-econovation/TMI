import re 
import collections
import numpy as np
from nltk.stem import PorterStemmer
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

## for BERT model
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import TfidfVectorizer
from sentence_transformers import SentenceTransformer

model_name = 'bert-base-nli-mean-tokens'
model = SentenceTransformer(model_name)


pstemmer = PorterStemmer()

def proxyLabelAssignement(UserX= None,UserY= None):
    if UserX is None or UserY is None:
        print("Missing arguments, might wanna check. ")
        #print(" \
            #input: UserX, UserY dictionary as created from parser func \n \
            #output: compatibility score \n \
            #function: assign score purely on the basis of pets preference \n \
            #---------------------------Assumption------------------- \n \
            #Orientatin and language based filtering is already done \n \
            #-------------------------------------------------------------")
        return 0
    
    essay_score = []
    
    essay_score.append(essay_cosine_score(UserX['essay0'], UserY['essay0']))
    essay_score.append(essay_cosine_score(UserX['essay1'], UserY['essay1']))
    essay_score.append(essay_cosine_score(UserX['essay2'], UserY['essay2']))
    essay_score.append(essay_cosine_score(UserX['essay3'], UserY['essay3']))
    essay_score.append(essay_cosine_score(UserX['essay4'], UserY['essay4']))
    essay_score.append(essay_cosine_score(UserX['essay5'], UserY['essay5']))
    essay_score.append(essay_cosine_score(UserX['essay6'], UserY['essay6']))
    essay_score.append(essay_cosine_score(UserX['essay7'], UserY['essay7']))
    essay_score.append(essay_cosine_score(UserX['essay8'], UserY['essay8']))
    essay_score.append(essay_cosine_score(UserX['essay9'], UserY['essay9']))
    
    # print(essay_score)
    score = sum(essay_score)/ float(len(essay_score))
    return score


def stemming(tokens):
    return [pstemmer.stem(token) for token in tokens]

def caseFolding(tokens):
    return [token.lower() for token in tokens]

def essay_cosine_score(x,y, method="BERT"):
    if method=="TF-IDF":
        global pstemmer
        #TODO: experiment with TF-IDF, manhattan and pearson distance
        #TF score- cosine formula for now
        x= stemming(x)
        x= caseFolding(x)
        y= stemming(y)
        y= caseFolding(y)

        dict_x= collections.defaultdict(int)
        dict_y= collections.defaultdict(int)

        for token in x:
            dict_x[token]+=1
        for token in y:
            dict_y[token]+=1
        mag_x= sum([value**2 for value in dict_x.values()])
        mag_y= sum([value**2 for value in dict_y.values()])
        set_tokens = set(dict_x.keys())| set(dict_y.keys())
        cosine_score= 0.0

        for token in set_tokens:
            cosine_score+= float(dict_x[token]*dict_y[token])
        cosine_score= cosine_score/float(mag_x*mag_y) if mag_x!=0 and mag_y!=0 else 0

    elif method == "BERT":
        x_vec = model.encode(x)
        y_vec = model.encode(y)

        cosine_score = cosine_similarity([x_vec], [y_vec])

    return cosine_score
        

def cos_similarity(v1, v2):
    dot_product = np.dot(v1, v2)
    l2_norm = (np.sqrt(sum(np.square(v1))) * np.sqrt(sum(np.square(v2))))
    similarity = dot_product / l2_norm

    return similarity
    

    
