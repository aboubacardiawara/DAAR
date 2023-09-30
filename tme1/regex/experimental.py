import matplotlib.pyplot as plt

lignes1 = []
lignes2 = []
with open('size_result_experimental_deterministic.txt', 'r') as file1:
        lignes1 = file1.readlines()
with open('size_result_experimental_epsilon.txt', 'r') as file2:
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
        with open('Time_AFD.txt', 'r') as file3:
                lignes3 = file3.readlines()
        with open('Time_Egrep.txt', 'r') as file4:
                lignes4 = file4.readlines()
        n=5
        x = list(range(1,n+1))
        y = [int(ligne.strip()) for ligne in lignes3]
        plt.plot(x,y) #afd 
        y = [int(ligne.strip()) for ligne in lignes4]
        plt.plot(x,y) #egrep

plt.show()

