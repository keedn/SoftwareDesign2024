import Lake
open Lake DSL

package «homework6-template» where
  -- add package configuration options here

lean_lib Problems where
  -- add library configuration options here

lean_lib Test where

@[default_target]
lean_exe «homework6-template» where
  root := `Main
