# Gestionnaire de Villes et Routes pour la Communauté d'Agglomération

## Étapes de Fonctionnement

1. **Lancement du Programme :** Exécutez la classe `Main` pour démarrer le gestionnaire de la Communauté d'Agglomération.

2. **Configuration de la Communauté :**
    - Choisissez l'option 1 pour configurer la communauté.
    - Indiquez le nombre de villes dans la CA.
    - Entrez le nom de chaque ville, en suivant les instructions.
    - Terminez la configuration en choisissant l'option 3 dans le menu de configuration.

3. **Ajout de Routes :**
    - Choisissez l'option 1 dans le menu principal.
    - Indiquez le nom de la ville de départ et d'arrivée pour chaque route.

4. **Ajout/Retrait de Zones de Recharge :**
    - Choisissez l'option 2 dans le menu principal.
    - Indiquez le nom de la ville pour ajouter ou retirer une zone de recharge.
    - Suivez les instructions pour ajouter ou retirer une zone.

5. **Sauvegarde et Chargement :**
    - Choisissez l'option 2 pour sauvegarder la communauté dans un fichier ou l'option 3 pour charger à partir d'un fichier.
    - Le fichier est sauvegardé ou chargé dans le répertoire spécifié.

6. **Fin du Programme :**
    - Choisissez l'option 4 pour quitter le programme.

## Corrections et Améliorations

- **Prise en Compte de la Casse:**
  - Les noms de villes sont désormais insensibles à la casse.
  - La vérification des doublons a été améliorée pour éviter les noms de villes en majuscules ou minuscules différents.

- **Affichage Approprié:**
  - Lors de l'ajout de routes, seul le nom de la ville est affiché, au lieu du chemin complet.

- **Gestion des Doublons:**
  - Lors de la configuration, si une ville avec le même nom existe déjà (indépendamment de la casse), un message d'erreur est affiché, et aucune nouvelle ville n'est ajoutée.

## Structure du Projet

- La gestion de la Communauté est centralisée dans la classe `Community`.
- Les villes sont représentées par la classe `City`.
- Les zones de recharge sont gérées par la classe `RechargeZone`.
- L'observateur `RechargeZoneObserver` réagit aux changements dans la Communauté.

## Auteurs

- Wassim CHIKHI
- Maciva Moubarki


## Licence

Ce projet est sous licence MIT.

## Notes Additionnelles pour l'Étudiant

Le projet a été amélioré pour répondre aux exigences spécifiques. Les corrections apportées sont décrites dans la section "Corrections et Améliorations". Assurez-vous de suivre les étapes détaillées pour tirer le meilleur parti du gestionnaire de la Communauté d'Agglomération.
