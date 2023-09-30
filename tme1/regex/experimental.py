import matplotlib.pyplot as plt

file1= open('size_result_experimental_deterministic.txt', 'r')
file2= open('size_result_experimental_epsilon.txt', 'r')
lignes1 = file1.readlines()
lignes2 = file2.readlines()
n=10
show_Time=True 
if (not show_Time):
        x = list(range(1,n+1))

        y = [int(ligne.strip()) for ligne in lignes1]

        plt.plot(x,y)

        y = [int(ligne.strip()) for ligne in lignes2]
        plt.plot(x,y)
else: 
        file3= open('Time_AFD.txt', 'r')
        file4= open('Time_Egrep.txt', 'r')
        lignes3 = file3.readlines()
        lignes4 = file4.readlines()
        n=5
        x = list(range(1,n+1))
        y = [int(ligne.strip()) for ligne in lignes3]
        plt.plot(x,y)
        y = [int(ligne.strip()) for ligne in lignes4]
        plt.plot(x,y)

plt.show()

