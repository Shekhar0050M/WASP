# import random as rnd
# import numpy as np
# import matplotlib.pyplot as plt
# from pyod.models.ecod import ECOD
# from pyod.utils.data import evaluate_print
# from pyod.utils.example import visualize
#
# # Create the dataset ( dumb dataset )
# ## Produce and process the random dataset
# frequency_list = [rnd.uniform(10.0, 200.0) for _ in range(1000)]
# half_length = len(frequency_list) // 2
# list_1 = frequency_list[:]
# # list_1 = frequency_list[:half_length]
# # list_2 = frequency_list[half_length:]
# X_train = np.array(list_1).reshape(-1, 2)
# # Y_train = np.array(list_2).reshape(-1, 2)
#
# # Process the dataset
# # train ECOD detector
# clf_name = 'ECOD'
# clf = ECOD()
# clf.fit(X_train)
# # get the prediction labels and outlier scores of the training data
# x_train_pred = clf.predict(X_train)  # binary labels (0: inliers, 1: outliers)
# x_train_scores = clf.decision_function(X_train)  # raw outlier scores
# inliers = X_train[X_train == 0]
# outliers = X_train[X_train == 1]
#
# # Plot the dataset
# plt.figure()
# plt.scatter(X_train[:, 0], X_train[:, 1], c='lightblue', label='X_train', alpha=0.5)
# if inliers.size > 0:
#     plt.scatter(inliers[:, 0], inliers[:, 1], c='blue', label='Inliers')
# if outliers.size > 0:
#     plt.scatter(outliers[:, 0], outliers[:, 1], c='red', marker='x', label='Outliers')
# plt.xlabel('Feature 1')
# plt.ylabel('Feature 2')
# plt.title('ECOD Outlier Detection')
# plt.legend()
# plt.show()

k = 1000000