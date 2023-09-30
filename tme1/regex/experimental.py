import matplotlib.pyplot as plt
file1= open('result_experimental_deterministic.txt', 'r')
file2= open('result_experimental_epsilon.txt', 'r')
lignes1 = file1.readlines()
lignes2 = file2.readlines()
n=10
x = list(range(1,n+1))

y = [int(ligne.strip()) for ligne in lignes1]

plt.plot(x,y)

y = [int(ligne.strip()) for ligne in lignes2]
plt.plot(x,y)

plt.show()
