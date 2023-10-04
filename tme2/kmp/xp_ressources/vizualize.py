import matplotlib.pyplot as plt

# Initialiser des listes pour stocker les données
nb_line = []
naive_duration = []
lps_duration = []
optimized_lps_duration = []

# Ouvrir le fichier en mode lecture
with open('../xp_result.csv', 'r') as file:
    # Lire les lignes du fichier
    lines = file.readlines()

# Parcourir les lignes et extraire les données
for line in lines[1:]:  # Ignorer la première ligne (en-tête)
    parts = line.strip().split()  # Diviser la ligne en valeurs en utilisant des espaces
    nb_line.append(int(parts[0]))
    naive_duration.append(int(parts[1]))
    lps_duration.append(int(parts[2]))
    optimized_lps_duration.append(int(parts[3]))

# Créer la figure et les sous-graphiques
fig, ax = plt.subplots()

# Tracer les données
ax.plot(nb_line, naive_duration, label='naive_duration')
ax.plot(nb_line, lps_duration, label='lps_duration')
ax.plot(nb_line, optimized_lps_duration, label='optimized_lps_duration')

# Ajouter des titres et une légende
ax.set_title('Comparaison des Durées')
ax.set_xlabel('nb_line')
ax.set_ylabel('Durée')
ax.legend()

# Afficher le graphique
plt.show()
