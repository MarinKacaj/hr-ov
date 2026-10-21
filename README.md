# HR OV Management App - Marin Kaçaj

## Structure

For such a simple app with time constraints, a single module structure
seemed good enough. There's just one `app` module. 
Layers can be found in packages.
* data is separate and common
* presentation and domain are inside each respective feature
* the repository handles data ↔ domain mapping

```plaintext
root-project/app
├── data/
│   ├── model/            # Data models (EmployeePublicTransportSituation, etc.)
│   └── network/          # Fake network
│
├── feature/
│   ├── employeeList/     # Employee list screen and view model < presentation; use case separate
│   ├── employeeDetail/   # Employee detail screen and view model, ...
│   └── renewals/         # Renewals screen and view model, ...
│
└── common/               # Basic common stuff
```

_Disclosure: the above plaintext snippet's template (not content) is AI-generated_