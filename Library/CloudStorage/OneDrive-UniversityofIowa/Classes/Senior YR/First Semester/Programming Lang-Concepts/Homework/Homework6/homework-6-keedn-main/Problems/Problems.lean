

-- You will need to use the following tactics:
-- `intro` `simp` `apply` `cases` `case _ =>` `injection`
-- `subst` `induction` `generalize`
-- You will benefit from using the combinators: `;` and `<;>`

def NormQuot {T : Type} (norm : T -> T) := { x : T // norm x = x}

-- Define a `to_even` function by recursion that is idempotent and satisfies
-- the tests below
@[simp]
def to_even : Nat -> Nat
| 0 => 0
| 1 => 0
| (n + 2) => to_even n + 2


theorem test1 : to_even 0 = 0 := by rfl
theorem test2 : to_even 1 = 0 := by rfl
theorem test3 : to_even 2 = 2 := by rfl
theorem test4 : to_even 3 = 2 := by rfl
theorem test5 : to_even 4 = 4 := by rfl

-- Define `to_odd` in terms of `to_even`
@[simp]
def to_odd (x : Nat) : Nat := to_even x + 1

theorem test6 : to_odd 0 = 1 := by rfl
theorem test7 : to_odd 1 = 1 := by rfl
theorem test8 : to_odd 2 = 3 := by rfl
theorem test9 : to_odd 3 = 3 := by rfl
theorem test10 : to_odd 4 = 5 := by rfl

-- Prove that `to_even` is idempotent using functional induction
-- Hint: start with `induction x using to_even.induct`
@[simp]
theorem to_even_idempotent : to_even (to_even x) = to_even x := by
  induction x using to_even.induct
  case _ =>
    simp
  case _ =>
    simp
  case _ n ih =>
    simp
    exact ih

-- Prove that `to_odd` is idempotent using functional induction on `to_even`
-- Hint: start with `simp` or `unfold to_odd`
@[simp]
theorem to_odd_idempotent : to_odd (to_odd x) = to_odd x := by
  unfold to_odd

  -- reduce the problem to the inner equality
  have h : to_even (to_even x + 1) = to_even x := by -- looked up this lean proof online
    induction x using to_even.induct
    case _ => simp
    case _ => simp
    case _ n ih => simp; exact ih

  -- finish by rewriting with the inner equality
  simp [h]



-- Now we can define the type of `Even` natural numbers
-- and the type of `Odd` natural numbers using the normalization functions
def Even := NormQuot to_even
def Odd := NormQuot to_odd

-- Fill in the remaining sorries for the definition of esucc
@[simp]
def esucc : Even -> Odd
| .mk e p => .mk (e + 1) (by {
  generalize zdef : e + 1 = z
  induction z using to_even.induct generalizing e <;> simp at *
  case _ n ih =>
    cases e
    case _ => injection zdef
    case _ e =>
      simp at *; cases e
      case _ =>
        subst zdef
        simp at *
      case _ e =>
        subst zdef
        replace ih := λ p => ih e p rfl
        simp [to_even] at p
        have := ih p
        simp [this]
})

-- Fill in the remaining sorries for the definition of osucc
@[simp]
def osucc : Odd -> Even
| .mk e p => .mk (e + 1) (by {
  generalize zdef : e + 1 = z
  induction z using to_even.induct generalizing e <;> simp at *
  case _ =>
    subst zdef
    simp [to_odd] at p
  case _ n ih =>
    cases e
    case _ => injection zdef
    case _ e =>
      simp at *; cases e
      case _ =>
        subst zdef
        simp [to_odd] at p
        simp [p]
      case _ e =>
        subst zdef
        replace ih := λ p => ih e p rfl
        simp [to_odd] at p
        have := ih p
        simp [this]
})

-- Define a succesor on `Even ⊕ Odd` using `esucc` and `osucc`
@[simp]
def eo_succ : Even ⊕ Odd -> Even ⊕ Odd :=
  fun x =>
    match x with
    | Sum.inl e => Sum.inr (esucc e)
    | Sum.inr o => Sum.inl (osucc o)

-- Define a transformation from `Nat` to `Even ⊕ Odd` using `eo_succ`
@[simp]
def nat_to_even_or_odd : Nat -> Even ⊕ Odd
| 0 => Sum.inl ⟨0, rfl⟩
| (n + 1) => eo_succ (nat_to_even_or_odd n)

-- Define a transformation from `Even ⊕ Odd` to `Nat`
--
-- Hint: try using the pattern `.inl (.mk e _)` and similarly for the other case
@[simp]
def even_or_odd_to_nat : Even ⊕ Odd -> Nat
| Sum.inl (.mk e _) => e
| Sum.inr (.mk o _) => o

-- From here you could prove that `Even ⊕ Odd ≅ Nat`
-- but the proof is a bit harder than what we've done in the past
