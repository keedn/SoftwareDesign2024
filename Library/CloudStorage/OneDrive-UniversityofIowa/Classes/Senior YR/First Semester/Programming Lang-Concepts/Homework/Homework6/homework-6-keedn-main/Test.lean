import Problems.Problems

def NormQuot' {T : Type} (norm : T -> T) := { x : T // norm x = x}

theorem NormQuot_same : NormQuot norm = NormQuot' norm := by rfl

#guard to_even 0 = 0
#guard to_even 1 = 0
#guard to_even 2 = 2
#guard to_even 3 = 2
#guard to_even 4 = 4

#guard to_odd 0 = 1
#guard to_odd 1 = 1
#guard to_odd 2 = 3
#guard to_odd 3 = 3
#guard to_odd 4 = 5

theorem to_even_idempotent_test : to_even (to_even x) = to_even x := to_even_idempotent

theorem to_odd_idempotent_test : to_odd (to_odd x) = to_odd x := to_odd_idempotent

def Even' := NormQuot to_even
def Odd' := NormQuot to_odd

theorem Even_same : Even = Even' := by rfl
theorem Odd_same : Odd = Odd' := by rfl

theorem esucc_def : (esucc e).1 = e.1 + 1 := by rfl
theorem osucc_def : (osucc o).1 = o.1 + 1 := by rfl

def eo_succ_type : Even ⊕ Odd -> Even ⊕ Odd := eo_succ

def nat_to_even_or_odd_type : Nat -> Even ⊕ Odd := nat_to_even_or_odd

def even_or_odd_to_nat_type : Even ⊕ Odd -> Nat := even_or_odd_to_nat

def id' := even_or_odd_to_nat ∘ nat_to_even_or_odd

#guard id' 0 = 0
#guard id' 1 = 1
#guard id' 2 = 2
#guard id' 3 = 3
#guard id' 4 = 4
#guard id' 5 = 5
#guard id' 10 = 10
#guard id' 11 = 11
#guard id' 111 = 111
#guard id' 123 = 123
#guard id' 512 = 512


-- namespace Algebraic
--   @[simp]
--   def Fin' (n : Nat) := { x : Nat // x < n}
--   theorem fin_def_unchanged : Fin n = Fin' n := by simp
--   theorem three_is_fin_3_test : Three ≅ Fin 3 := three_is_fin_3
-- end Algebraic

-- #check Term.S
-- #check Term.K
-- #check Term.I
-- #check λ (a b : Term) => Term.app a b

-- #check Red.s
-- #check Red.k
-- #check Red.i
-- #check Red.cong1
-- #check Red.cong2

-- theorem S_law : .S ⬝ a ⬝ b ⬝ c ~> a ⬝ c ⬝ (b ⬝ c) := Red.s
-- theorem K_law : .K ⬝ a ⬝ b ~> a := Red.k
-- theorem I_law : .I ⬝ a ~> a := Red.i
-- theorem cong1_law : a ~> a' -> a ⬝ b ~> a' ⬝ b := Red.cong1
-- theorem cong2_law : b ~> b' -> a ⬝ b ~> a ⬝ b' := Red.cong2

-- theorem refl_law : a ~>* a := RedStar.refl
-- theorem step_law b : a ~> b -> b ~>* c -> a ~>* c := RedStar.step b

-- namespace Term
--   def T' := K
--   def F' := S ⬝ K
--   def OR' := S ⬝ I ⬝ (K ⬝ T)
--   def AND' := S ⬝ S ⬝ (K ⬝ (K ⬝ F))
--   def NOT' := S ⬝ (S ⬝ I ⬝ (K ⬝ F)) ⬝ (K ⬝ T)

--   theorem T_def : T = T' := by
--   unfold T; unfold T'; simp

--   theorem F_def : F = F' := by
--   unfold F; unfold F'; simp

--   theorem OR_def : OR = OR' := by
--   unfold OR; unfold OR'; simp

--   theorem AND_def : AND = AND' := by
--   unfold AND; unfold AND'; simp

--   theorem NOT_def : NOT = NOT' := by
--   unfold NOT; unfold NOT'; simp

--   theorem or_true_true_step_test : OR ⬝ T ⬝ T ~>* T := or_true_true_step
--   theorem or_true_false_step_test : OR ⬝ T ⬝ F ~>* T := or_true_false_step
--   theorem or_false_true_step_test : OR ⬝ F ⬝ T ~>* T := or_false_true_step
--   theorem or_false_false_step_test : OR ⬝ F ⬝ F ~>* F := or_false_false_step
-- end Term
