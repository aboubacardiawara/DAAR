import matplotlib.pyplot as plt

lignes1 = []
lignes2 = []
with open('size_result_experimental_AFD_non_optimisé.txt', 'r') as file1:
        lignes1 = file1.readlines()
with open('size_result_experimental_epsilon.txt', 'r') as file2:
        lignes2 = file2.readlines()
with open('size_result_experimental_AFD_optimise.txt', 'r') as file6:
        lignes6 = file6.readlines()

n=10
show_Time_by_file_size=False 
show_auomate_size=False 
show_time_by_regex=False
show_time_by_regex_files= True

if show_auomate_size:
        x = list(range(1,n+1))
        y = [int(ligne.strip()) for ligne in lignes1]
        plt.plot(x,y, c='red', label='AFD_non_optimisé')
        yy = [int(ligne.strip()) for ligne in lignes2]
        plt.plot(x,yy, c='blue', label='epsilon')
        yyy=[int(ligne.strip()) for ligne in lignes6]
        plt.plot(x,yyy, c='green', label='AFD_optimisé')

        plt.title('comparaison de la taille des automates avec epsolon sans epsilon')
        plt.xlabel('regex') 
        plt.ylabel('nombre détat dans l automate')
        plt.legend(prop = {'size': 10})
        plt.savefig('size_automate.png')
        
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
        plt.bar(x,y, color='red') #afd 
        yy = [int(ligne.split()[1]) for ligne in lignes4]
        plt.xlabel('files')
        plt.ylabel('temps d execution en ms')
        plt.title('Diagramme du temps dexecution sur la meme regex pour différentq file')
        plt.bar(x,yy,  color='blue') #egrep
        plt.legend(['AFD', 'Egrep'])
        plt.savefig('files_time.png')

if show_time_by_regex_files:
        with open('Time_AFD_average.txt', 'r') as file8:
                lignes8 = file8.readlines()
        with open('Time_Egrep_average.txt', 'r') as file9:
                lignes9 = file9.readlines()   
        largeur_barre = 0.5  
        x = list(range(1,len(lignes9)+1))
        plt.title('moyenne du temps d execution pour differents regex')
        plt.xlabel('regex') 
        plt.ylabel('Time in ms')
        y= [int(ligne.split()[1]) for ligne in lignes8]
        plt.bar(x,y,color ='red', width=largeur_barre, label='AFD') #afd 
        yy = [int(ligne.split()[1]) for ligne in lignes9]
        plt.bar([i + largeur_barre for i in x], yy, width=largeur_barre, label='Egrep')
        plt.legend()
        plt.savefig('time_average.png')
