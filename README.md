# Ohjelmistoprojekti

## Project information

| Info | Description|
| ------ | ----------- |
| Project name | Electricity Exchange Price App |
| Project description | An Android application for checking electricity prices<br>and extracting hours with the lowest prices to run electrical devices.<br>The app also includes price alerts. |
| Team members | Mikko Paaso, Daniil Kovalev, Waltteri Lehtinen |

  ## Development instructions
  ### Tools and Software needed
    Android Studio, VS Code, Git Bash, (for local backend Python 3.9.12)
  
  ### Clone project
    $ git clone git@github.com:Rolppe/Ohjelmistoprojekti.git

  ## Install Android App
  Instructions here

  ## Open project in Android Studio
  1. Open Android Studio
  2. Click open
  3. Navigate to your cloned project repository folder
  4. Click once Ohjelmistoprojecti folder to highlight it
  5. Click open button in right bottom corner of window  
  6. When you get prompted Trust and Open project, Click "Trust Project" button.
  7. Create

  ## Backend is running on Railway.app (no need for local deployment)
  
  ## Optionally you can deploy the backend locally as follows
  
  ### Enter project folder
    $ cd Ohjelmistoprojekti
  
  ### Activate virtual environment: 
    $ . backend/.venv/bin/activate
    
  ### Run Server: 
    $ python backend/djangoproject/manage.py runserver
    
  ### Check json output at browser
  ![Screen Shot 2022-12-10 at 15 37 57](https://user-images.githubusercontent.com/78311409/206858122-40094a7a-a103-41bc-b00c-1e0a9857e42a.jpeg)

    < development server at >/pricejson

  example:
    
    http://127.0.0.1:8000/pricejson

  ## Links to deployment servers on Railway.app
  
    - JSON request: 
    https://ohjelmistoprojekti-production.up.railway.app/pricejson/
    
    - Administration 
    https://ohjelmistoprojekti-production.up.railway.app/admin/
