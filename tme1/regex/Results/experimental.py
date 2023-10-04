import matplotlib.pyplot as plt

lignes1 = []
lignes2 = []
with open('size_result_experimental_deterministic.txt', 'r') as file1:
        lignes1 = file1.readlines()
with open('size_result_experimental_epsilon.txt', 'r') as file2:
        lignes2 = file2.readlines()

n=10
show_Time_by_file_size=False 
show_auomate_size=False 
show_time_by_regex=True

if show_auomate_size:
        x = list(range(1,n+1))
        y = [int(ligne.strip()) for ligne in lignes1]
        plt.plot(x,y)
        y = [int(ligne.strip()) for ligne in lignes2]
        plt.plot(x,y)

if show_Time_by_file_size:
        with open('Time_AFD.txt', 'r') as file3:
                lignes3 = file3.readlines()
        with open('Time_Egrep.txt', 'r') as file4:
                lignes4 = file4.readlines()
        n=5
        x= [int(ligne.split()[0]) for ligne in lignes3]
        plt.title('comparaison du temps d execution entre egrep et AFD')
        plt.xlabel('file size MB') 
        plt.ylabel('Time in ms')
        y= [int(ligne.split()[1]) for ligne in lignes3]
        plt.plot(x,y,c ='red' , label ="afd") #afd 
        yy = [int(ligne.split()[1]) for ligne in lignes4]
        plt.plot(x,yy,c ='blue' , label ="egrep") #egrep
        plt.legend(prop = {'size': 10})

if show_time_by_regex:
        with open('Time_AFD.txt', 'r') as file3:
                lignes3 = file3.readlines()
        with open('Time_Egrep.txt', 'r') as file4:
                lignes4 = file4.readlines()        
        x = list(range(1,len(lignes3)+1))
        y= [int(ligne.split()[1]) for ligne in lignes3]
        plt.plot(x,y,c ='red' , label ="afd") #afd 
        yy = [int(ligne.split()[1]) for ligne in lignes4]
        plt.plot(x,yy,c ='blue' , label ="egrep") #egrep


plt.savefig('xp.png')