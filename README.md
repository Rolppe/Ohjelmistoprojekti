# Ohjelmistoprojekti

## Project information

| Info | Description|
| ------ | ----------- |
| Project name | Electricity Exchange Price App |
| Project description | An Android application for checking electricity prices<br>with an ability to extract current day's hours<br>with the lowest prices to run electrical devices. |
| Team members | Mikko Paaso, Daniil Kovalev, Waltteri Lehtinen |

  ## Development instructions
  ### Tools and Software needed
    Android Studio (Dolphin | 2021.3.1 (or newer)), VS Code, Git Bash, (for local backend Python 3.9.12)
  
  ### Clone project
    $ git clone git@github.com:Rolppe/Ohjelmistoprojekti.git

  ## Install Android App
  1. Download the apk to your laptop or desktop.
  2. Plug in your phone via usb 
  3. Navigate to an easy to find forlder in your mobile device's internal storage (I.E downloads folder)
  4. Drag and drop the apk into your chosen folder on your mobile device (alternatively you can send the apk to your mobile decie wirelessly if you prefer, this option of course assumes you know how to do that.)
  5. On your mobile device naviage to the folder you copied the apk into
  6. Press the apk
  6.1. You might be required to give permissions to allow you to install an apk from your mobile device's file system
  6.2 Give permission to your file system to allow it to install apps
  7. Press install
  8. Wait for install to finish
  9. Press "Done", and find the app in your apps list, or press "Open" to open the app straight from the installatin completion dialog. 

  ## Open project in Android Studio
  1. Open Android Studio
  2. Click open
  3. Navigate to your cloned project repository folder
  4. Click once Ohjelmistoprojecti folder to highlight it
  5. Click open button in right bottom corner of window  
  6. When you get prompted Trust and Open project, Click "Trust Project" button.
  7. Create
  8. Sync project with Gradle files

  ## Backend is running on Railway.app (no need for local deployment)
  ### Admin logins:
  
  **Webpage address:**
  https://ohjelmistoprojekti-production.up.railway.app/admin/
  
  **Username:** mikko
  
  **Password:** ohjelmistoprojekti2022
  
  **NOTICE:** At admin page Day prices includes all the price data
  
  ## Optionally you can deploy the backend locally as follows
  
  ### Enter the project folder
    $ cd Ohjelmistoprojekti
  
  ### Activate virtual environment: 
    $ . backend/.venv/bin/activate
    
  ### Run Server: 
    $ python backend/djangoproject/manage.py runserver
    
  ### Check JSON output at browser
  ![Screen Shot 2022-12-10 at 15 37 57](https://user-images.githubusercontent.com/78311409/206858122-40094a7a-a103-41bc-b00c-1e0a9857e42a.jpeg)

    < development server at >/pricejson

  example:
    
    http://127.0.0.1:8000/pricejson
  
  ### Admin logins:
  
  **Webpage address:**
  
      < development server at >/admin

  example:
    
    http://127.0.0.1:8000/admin/
  
  **Username:** mikko
  
  **Password:** ohjelmistoprojekti2022
  
  **NOTICE:** At admin page Day prices includes all the price data

  ## Links to deployment servers on Railway.app
  
  * **JSON request:** https://ohjelmistoprojekti-production.up.railway.app/pricejson/
    
  * **Administration:** https://ohjelmistoprojekti-production.up.railway.app/admin/
