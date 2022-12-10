# Ohjelmistoprojekti

Project information

  Project name: Electricity Exchange Price APP
    
  Project description: Android APP for checking 
    electricity prices and extracting best prices 
    to run electrical devises. It also includes price alerts
    
  Team members: Mikko Paaso, Daniil Kovalev, Waltteri Lehtinen
  
  ## Development instructions
  ### Tools and Software needed
    Android Studio, VS Code, Git Bash, (for local backend Python 3.9.12)
  
  ### Clone project
    $ git clone git@github.com:Rolppe/Ohjelmistoprojekti.git

  ## Backend is running on railway.app (no need for local deployment)
  
  ## Optionally run backend locally
  
  ### Enter project folder
    $ cd Ohjelmistoprojekti
  
  ### Activate virtual environment: 
    $ . backend/.venv/bin/activate
    
  ### Run Server: 
    $ python backend/djangoproject/manage.py runserver
    
  ### Check json output
  ![Screen Shot 2022-12-10 at 15 37 57](https://user-images.githubusercontent.com/78311409/206858122-40094a7a-a103-41bc-b00c-1e0a9857e42a.jpeg)

    $ {development server at}pricejson

  ## Links to deployment servers on railway.app
  
    - JSON request: 
    https://ohjelmistoprojekti-production.up.railway.app/pricejson/
    
    - Administration 
    https://ohjelmistoprojekti-production.up.railway.app/admin/
