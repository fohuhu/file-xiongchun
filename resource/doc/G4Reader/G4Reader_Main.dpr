program G4Reader_Main;

uses
  Forms,
  G4Reader in 'G4Reader.pas' {MainForm};

{$R *.res}

begin
  Application.Initialize;
  Application.CreateForm(TMainForm, MainForm);
  Application.Run;
end.
